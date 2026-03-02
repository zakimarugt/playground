namespace UserAccountApiTest.Domain

open NUnit.Framework
open UserAccountApi.Domain
open UserAccountApi.Domain.UserAccount

module UserAccountTest =
    [<Test>]
    let ``未検証のユーザーアカウントのメールアドレスとトークンが一致する場合、検証が成功したという結果を返す`` () =
        let mailAddress = MailAddress "test@example.com"
        let verificationToken = VerificationToken.UnVerified (UnVerifiedVerificationToken "some-verification-token")
        let existingUnVerifiedUserAccount = UserAccount.UnVerified (UnVerifiedUserAccount (mailAddress, verificationToken))
        let unVerifiedUserAccount = UnVerifiedUserAccount (mailAddress, verificationToken)
        
        let actual = verify existingUnVerifiedUserAccount unVerifiedUserAccount
        
        match actual with
        | Ok _ -> ()
        | Error _ -> Assert.Fail("Expected result is Ok, but not.")
    
    [<Test>]
    let ``未検証のユーザーアカウントのメールアドレスとトークンが一致しない場合、検証が失敗したという結果を返す`` () =
        let mailAddress = MailAddress "test@example.com"
        let verificationToken = VerificationToken.UnVerified (UnVerifiedVerificationToken "some-verification-token")
        let existingUnVerifiedUserAccount = UserAccount.UnVerified (UnVerifiedUserAccount (mailAddress, verificationToken))
        let unVerifiedUserAccount = UnVerifiedUserAccount (MailAddress "different@example.com", verificationToken)
        
        let actual = verify existingUnVerifiedUserAccount unVerifiedUserAccount
        
        match actual with
        | Ok _ -> Assert.Fail("Expected result is Error, but not.")
        | Error _ -> ()
        
    [<Test>]
    let ``検証済みのユーザーアカウントの場合は、検証が成功したという結果を返す`` () =
        let userAccountId = UserAccountId.from "911e5341-78bc-4481-c93e-141326496c69"
        let mailAddress = MailAddress "test@example.com"
        let userAccountName = UserAccountName "test"
        let userAccountPassword = UserAccountPassword "asdfasdf"
        
        let verificationToken = VerificationToken.UnVerified (UnVerifiedVerificationToken "some-verification-token")
        let existingVerifiedUserAccount = UserAccount.Verified (VerifiedUserAccount (userAccountId, mailAddress, userAccountName, userAccountPassword))
        let unVerifiedUserAccount = UnVerifiedUserAccount (mailAddress, verificationToken)
        
        let actual = verify existingVerifiedUserAccount unVerifiedUserAccount
        
        match actual with
        | Ok _ -> ()
        | Error _ -> Assert.Fail("Expected result is Ok, but not.")
