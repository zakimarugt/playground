namespace UserAccountApi.InterfaceAdapter

open FsToolkit.ErrorHandling
open Microsoft.AspNetCore.Http
open UserAccountApi.Domain
open UserAccountApi.Application

module CreateUserAccountHandler =
    type CreateUnVerifiedUserAccountRequestJson = {
        mailAddress: string
    }
    
    let validate (requestJson: CreateUnVerifiedUserAccountRequestJson) =
        validation {
            let! userAccountMailAddressValidationResult = MailAddress.validate requestJson.mailAddress |> Result.mapError List.singleton
            return userAccountMailAddressValidationResult 
        }
        
    let handle (dependencies: CreateUserAccountUseCase.Dependencies) (requestJson: CreateUnVerifiedUserAccountRequestJson): Async<IResult> =
        async {
            match validate requestJson with
            | Ok _ ->
                let UnVerifiedUserAccount = UserAccount.createUnVerifiedUserAccount (MailAddress requestJson.mailAddress)
                do! CreateUserAccountUseCase.execute dependencies UnVerifiedUserAccount
                return Results.NoContent()
            | Error validationResults -> return Results.BadRequest(validationResults)
        }
