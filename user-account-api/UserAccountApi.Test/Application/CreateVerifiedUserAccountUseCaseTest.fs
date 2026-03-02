namespace UserAccountApi.Test.Application

open System
open NUnit.Framework
open UserAccountApi.Application.CreateVerifiedUserAccountUseCase
open UserAccountApi.Domain

module CreateVerifiedUserAccountUseCaseTest =
    [<Test>]
    let ``認証に成功した場合は、ユーザーアカウントを認証する`` () =
        let mutable verifiedUserAccountRegistrationCount = 0
        
        let mailAddress = MailAddress "test@example.com"
        let expiredDate = ExpiredDate (DateTime.Parse "2026-02-15 00:00:00")
        let effectiveVerificationToken = VerificationToken.Effective (EffectiveVerificationToken ("effective-token-value", expiredDate))
        let unVerifiedUserAccount = UnVerifiedUserAccount (mailAddress, effectiveVerificationToken)
        
        let userAccountName = UserAccountName "test"
        let userAccountPassword = UserAccountPassword "asdf"
        
        let dependencies: Dependencies = {
            save = fun (userAccount: UserAccount) ->
                async {
                    match userAccount with
                    | UnVerified _ -> "The condition of triggering mock is not correct." |> ignore
                    | UserAccount.Verified (VerifiedUserAccount (_, _mailAddress, _userAccountName, _userAccountPassword)) ->
                        if _mailAddress = mailAddress && _userAccountName = userAccountName && _userAccountPassword = userAccountPassword
                        then verifiedUserAccountRegistrationCount <- verifiedUserAccountRegistrationCount + 1
                        else return failwith "The condition of triggering mock is not correct."
                }
            findBy = fun (mailAddress: MailAddress) ->
                async {
                    if mailAddress = UserAccount.mailAddress (UserAccount.UnVerified unVerifiedUserAccount)
                    then return UserAccount.UnVerified (UnVerifiedUserAccount (mailAddress, effectiveVerificationToken))
                    else return failwith "The condition of triggering mock is not correct."
                }
        }
     
        execute dependencies unVerifiedUserAccount userAccountName userAccountPassword
        |> Async.RunSynchronously
        |> ignore

        Assert.That(verifiedUserAccountRegistrationCount, Is.EqualTo(1))

    [<Test>]
    let ``認証に失敗した場合は、そのことが分かる結果を返す``() =
        let mutable verifiedUserAccountRegistrationCount = 0
        
        let mailAddress = MailAddress "test@example.com"
        let expiredDate = ExpiredDate (DateTime.Parse "2026-02-15 00:00:00")
        let effectiveVerificationToken = VerificationToken.Effective (EffectiveVerificationToken ("effective-token-value", expiredDate))
        let unVerifiedUserAccount = UnVerifiedUserAccount (mailAddress, effectiveVerificationToken)
        
        let userAccountName = UserAccountName "test"
        let userAccountPassword = UserAccountPassword "asdf"
        
        let dependencies: Dependencies = {
            save = fun _ -> async { return () }
            findBy = fun _ ->
                async {
                    return UserAccount.UnVerified (UnVerifiedUserAccount (MailAddress "different@example.com", effectiveVerificationToken))
                }
        }
        
        let actual: Result<unit, UserAccountVerificationError> =
            execute dependencies unVerifiedUserAccount userAccountName userAccountPassword
            |> Async.RunSynchronously
        
        
        match actual with
        | Ok _ -> Assert.Fail("The result is not expected one.")
        | Error error -> Assert.That(error, Is.EqualTo(UserAccountVerificationError("User account verification was failed.")))
