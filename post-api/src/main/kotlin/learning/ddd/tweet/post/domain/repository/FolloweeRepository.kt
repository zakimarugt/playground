package learning.ddd.tweet.post.domain.repository

import learning.ddd.tweet.post.domain.valueobject.UserAccountId
import learning.ddd.tweet.post.domain.valueobject.UserAccountIds

interface FolloweeRepository {
    fun findById(userAccountId: UserAccountId): UserAccountIds
}