package learning.ddd.tweet.post.domain.repository

import learning.ddd.tweet.post.domain.entity.useraccount.UserAccount
import learning.ddd.tweet.post.domain.valueobject.UserAccountId
import org.springframework.stereotype.Component

@Component
interface UserAccountRepository {
    fun findById(userAccountId: UserAccountId): UserAccount
}