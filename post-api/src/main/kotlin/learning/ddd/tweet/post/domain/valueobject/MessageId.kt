package learning.ddd.tweet.post.domain.valueobject

import java.util.UUID

sealed class MessageId(open val value: String) {
    data class PostId(override val value: String = UUID.randomUUID().toString()): MessageId(value)
    data class ResponseId(override val value: String = UUID.randomUUID().toString()): MessageId(value)
}

sealed class MessageIds(open val value: List<MessageId>) {
    data class PostIds(override val value: List<MessageId.PostId>): MessageIds(value)
    data class ResponseIds(override val value: List<MessageId.ResponseId>): MessageIds(value)
}
