package learning.ddd.tweet.post.domain.entity.useraccount

import learning.ddd.tweet.post.domain.valueobject.UserAccountId

typealias PersonalAccount = UserAccount.PersonalAccount
typealias CompanyAccount = UserAccount.CompanyAccount

class UserAccountFactory {
    companion object {
        // UserAccount自体に持たせても違和感はない...？
        fun reconstruct(userAccountId: UserAccountId, userAccountType: UserAccountType): UserAccount {
            return when (userAccountType) {
                UserAccountType.PERSONAL -> PersonalAccount(userAccountId)
                UserAccountType.COMPANY -> CompanyAccount(userAccountId)
            }
        }
    }
}