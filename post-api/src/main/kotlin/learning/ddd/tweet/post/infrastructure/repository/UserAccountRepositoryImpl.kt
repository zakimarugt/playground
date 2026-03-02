package learning.ddd.tweet.post.infrastructure.repository

import learning.ddd.tweet.post.domain.entity.useraccount.UserAccount
import learning.ddd.tweet.post.domain.repository.UserAccountRepository
import learning.ddd.tweet.post.domain.valueobject.UserAccountId
import learning.ddd.tweet.post.infrastructure.driver.UserAccountApiDriver
import learning.ddd.tweet.post.interfaceadapter.conversion.json.converter.UserAccountJsonConverter
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserAccountRepositoryImpl(private val userAccountApiDriver: UserAccountApiDriver): UserAccountRepository {
    @Transactional
    override fun findById(userAccountId: UserAccountId): UserAccount {
        return userAccountApiDriver.findBy(userAccountId.value)
            .let { UserAccountJsonConverter.from(it) }
    }
}