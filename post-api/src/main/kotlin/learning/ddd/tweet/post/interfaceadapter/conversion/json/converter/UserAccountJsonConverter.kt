package learning.ddd.tweet.post.interfaceadapter.conversion.json.converter

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import learning.ddd.tweet.post.domain.entity.useraccount.UserAccount
import learning.ddd.tweet.post.domain.entity.useraccount.UserAccountFactory
import learning.ddd.tweet.post.domain.entity.useraccount.UserAccountType
import learning.ddd.tweet.post.domain.valueobject.UserAccountId
import learning.ddd.tweet.post.domain.valueobject.UserAccountIds
import learning.ddd.tweet.post.infrastructure.json.UserAccountJson

class UserAccountIdConverter() {
    companion object {
        fun fromJson(json: String): UserAccountIds {
            val decoded = Json.decodeFromString<FolloweeIds>(json)
            return decoded.followeeIds
                .map { UserAccountId(it) }
                .let(::UserAccountIds)
        }
    }
}

class UserAccountJsonConverter {
    companion object {
        fun from(json: UserAccountJson): UserAccount {
            return UserAccountFactory.reconstruct(UserAccountId(json.id), UserAccountType.from(json.type))
        }
    }
}

@Serializable
private data class FolloweeIds(val followeeIds: List<String>)
