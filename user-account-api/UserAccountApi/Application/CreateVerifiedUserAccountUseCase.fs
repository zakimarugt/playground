namespace UserAccountApi.Application

open UserAccountApi.Domain

module CreateVerifiedUserAccountUseCase =
    type UserAccountVerificationError = UserAccountVerificationError of message: string
    type Dependencies = {
        save: UserAccountRepository.save
        findBy: UserAccountRepository.findBy
    }
    
    let execute (dependencies: Dependencies) (unVerifiedUserAccount: UnVerifiedUserAccount) (userAccountName: UserAccountName) (userAccountPassword: UserAccountPassword) : Async<Result<_, UserAccountVerificationError>> = async {
            // mailAddressを導出するときは、UnVerifiedでもVerifiedでもどっちでもいいので、使う側で意識させたくない...
            let! existingUnVerifiedUserAccount = dependencies.findBy (UserAccount.mailAddress (UnVerified unVerifiedUserAccount))
            match UserAccount.verify existingUnVerifiedUserAccount unVerifiedUserAccount with
            | Ok _ ->
                let verifiedUserAccount = UserAccount.createVerifiedUserAccount (UserAccount.mailAddress (UnVerified unVerifiedUserAccount)) userAccountName userAccountPassword
                do! dependencies.save verifiedUserAccount
                return Ok()
            | Error _ -> return Error(UserAccountVerificationError("User account verification was failed."))
        }
