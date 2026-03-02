package learning.ddd.tweet.post.domain.entity.response

import java.util.UUID

data class ResponderAccountId(val value: String = UUID.randomUUID().toString())