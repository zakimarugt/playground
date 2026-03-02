package learning.ddd.tweet.post.domain.repository

import learning.ddd.tweet.post.domain.entity.response.ExistingResponses
import learning.ddd.tweet.post.domain.entity.response.Response
import learning.ddd.tweet.post.domain.valueobject.MessageId

typealias ExistingResponse = Response.ExistingResponse

interface ResponseRepository {
    fun findBy(targetId: MessageId): ExistingResponses
    fun save(response: Response)
}