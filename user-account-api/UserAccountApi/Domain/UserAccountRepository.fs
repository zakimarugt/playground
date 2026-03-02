namespace UserAccountApi.Domain

module UserAccountRepository =
    type save = UserAccount -> Async<unit>
    type findBy = MailAddress -> Async<UserAccount>

