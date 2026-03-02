package learning.ddd.tweet.post.domain.valueobject

import java.lang.IllegalArgumentException
import java.util.UUID

data class UserAccountId(val value: String) {
    companion object {
        fun validate(value: String): Result<Unit> {
            return runCatching { UUID.fromString(value) }
                .fold(
                    { Result.success(Unit) },
                    { Result.failure(IllegalArgumentException("A value should be the type of UUID.")) }
                )
        }
    }
}

data class UserAccountIds(val values: List<UserAccountId>) {
    fun isEmpty(): Boolean {
        return values.isEmpty()
    }

    fun isNotEmpty(): Boolean {
        return values.isNotEmpty()
    }
}