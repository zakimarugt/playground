package learning.ddd.tweet.post.infrastructure.repository

import learning.ddd.tweet.post.domain.valueobject.UserAccountId
import learning.ddd.tweet.post.domain.valueobject.UserAccountIds
import learning.ddd.tweet.post.infrastructure.driver.UserAccountApiDriver
import org.springframework.stereotype.Repository

@Repository
class FolloweeRepository(private val userAccountApiDriver: UserAccountApiDriver): learning.ddd.tweet.post.domain.repository.FolloweeRepository {
    override fun findById(userAccountId: UserAccountId): UserAccountIds {
        return userAccountApiDriver.findFolloweeIds(userAccountId.value).followeeIds
            .map { UserAccountId(it) }
            .let(::UserAccountIds)
    }
}