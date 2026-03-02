namespace UserAccountApi.InterfaceAdapter

open FsToolkit.ErrorHandling
open Microsoft.AspNetCore.Http
open UserAccountApi.Application
open UserAccountApi.Application.CreateVerifiedUserAccountUseCase
open UserAccountApi.Domain
open UserAccountApi.InterfaceAdapter.CreateUserAccountHandler


module CreateVerifiedUserAccountHandler =
    type CreateVerifiedUserAccountRequestJson = {
        mailAddress: string
        userAccountName: string
        userAccountPassword: string
        verificationToken: string
    }
    
    let validate (requestJson: CreateVerifiedUserAccountRequestJson)=
        validation {
            let! mailAddressValidationResult = MailAddress.validate requestJson.mailAddress
            let! userAccountNameValidationResult = UserAccountName.validate requestJson.userAccountName
            let! userAccountPasswordValidationResult = UserAccountPassword.validate requestJson.userAccountPassword
            
            return mailAddressValidationResult, userAccountNameValidationResult, userAccountPasswordValidationResult
        }
        
        
    let handle (dependencies: Dependencies) (requestJson: CreateVerifiedUserAccountRequestJson) : Async<IResult> =
            async {
                let validationResult = validate requestJson
                match validationResult with
                | Ok _ ->
                    let unVerifiedUserAccount = UnVerifiedUserAccount (MailAddress requestJson.mailAddress, VerificationToken.UnVerified (UnVerifiedVerificationToken requestJson.verificationToken))
                    let userAccountName = UserAccountName requestJson.userAccountName
                    let userAccountPassword = UserAccountPassword requestJson.userAccountPassword
                    // verificationTokenに関するバリデーションを追加したい
                    
                    let! result = CreateVerifiedUserAccountUseCase.execute dependencies unVerifiedUserAccount userAccountName userAccountPassword
                    match result with
                    | Ok _ -> return Results.NoContent()
                    | Error (UserAccountVerificationError message) ->
                        return Results.BadRequest(message)

                | Error validationResults ->
                    return Results.BadRequest(validationResults)
            }
