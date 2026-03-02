namespace UserAccountApi.Application

open UserAccountApi.Domain

module CreateUserAccountUseCase =
    type Dependencies = { save: UserAccountRepository.save }
    
    let execute (dependencies: Dependencies) (unVerifiedUserAccount: UnVerifiedUserAccount): Async<unit> =
        async {
            do! dependencies.save (UnVerified unVerifiedUserAccount)
        }
