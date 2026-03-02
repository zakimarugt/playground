package learning.ddd.tweet.post.interfaceadapter.conversion.database

import learning.ddd.tweet.post.domain.entity.response.ExistingResponses
import learning.ddd.tweet.post.domain.entity.response.ResponderAccountId
import learning.ddd.tweet.post.domain.entity.response.Response
import learning.ddd.tweet.post.domain.repository.ExistingResponse
import learning.ddd.tweet.post.domain.valueobject.Content
import learning.ddd.tweet.post.domain.valueobject.LikeCount
import learning.ddd.tweet.post.domain.valueobject.MessageId
import learning.ddd.tweet.post.domain.valueobject.RepostedCount
import learning.ddd.tweet.post.infrastructure.entity.JpaResponseId
import learning.ddd.tweet.post.infrastructure.entity.JpaResponseToResponse

class ResponseToResponseConverter {
    companion object {
        fun from(domainResponse: Response): JpaResponseToResponse {
            return JpaResponseToResponse(
                domainResponse.id.value,
                domainResponse.responderAccountId.value,
                domainResponse.content.value,
                domainResponse.likeCount.value,
                domainResponse.repostedCount.value,
                JpaResponseId(domainResponse.targetId.value)
            )
        }

        fun from(jpaResponse: JpaResponseToResponse): ExistingResponse {
            return Response.ExistingResponse(
                MessageId.ResponseId(jpaResponse.id),
                ResponderAccountId(jpaResponse.responderAccountId),
                Content(jpaResponse.content),
                MessageId.ResponseId(jpaResponse.responseId.value),
                LikeCount(jpaResponse.likeCount),
                RepostedCount(jpaResponse.repostedCount)
            )
        }

        fun from(jpaResponses: List<JpaResponseToResponse>): ExistingResponses {
            return jpaResponses
                .map { from(it) }
                .let(::ExistingResponses)
        }
    }
}