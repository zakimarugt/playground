package learning.ddd.tweet.post.interfaceadapter.conversion.json.converter

import learning.ddd.tweet.post.domain.entity.response.ExistingResponses
import learning.ddd.tweet.post.domain.valueobject.MessageId
import learning.ddd.tweet.post.interfaceadapter.conversion.json.model.ResponseJson
import learning.ddd.tweet.post.interfaceadapter.conversion.json.model.ResponsesJson

class ResponsesJsonConverter {
    companion object {
        fun toJson(postId: MessageId.PostId, responses: ExistingResponses): ResponsesJson {
            return responses.values.map { response ->
                ResponseJson(
                    response.id.value,
                    response.responderAccountId.value,
                    response.content.value,
                    response.likeCount.value,
                    response.repostedCount.value
                )
            }.let { ResponsesJson(postId.value, it) }
        }
    }
}