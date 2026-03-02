package learning.ddd.tweet.post.interfaceadapter.conversion.database

import learning.ddd.tweet.post.domain.entity.response.ExistingResponses
import learning.ddd.tweet.post.domain.entity.response.ResponderAccountId
import learning.ddd.tweet.post.domain.entity.response.Response
import learning.ddd.tweet.post.domain.entity.response.Responses
import learning.ddd.tweet.post.domain.repository.ExistingResponse
import learning.ddd.tweet.post.domain.valueobject.Content
import learning.ddd.tweet.post.domain.valueobject.LikeCount
import learning.ddd.tweet.post.domain.valueobject.MessageId
import learning.ddd.tweet.post.domain.valueobject.RepostedCount
import learning.ddd.tweet.post.infrastructure.entity.JpaPostId
import learning.ddd.tweet.post.infrastructure.entity.JpaResponseToPost

class ResponseToPostConverter {
    companion object {
        fun from(domainResponse: Response): JpaResponseToPost {
            return JpaResponseToPost(
                domainResponse.id.value,
                domainResponse.responderAccountId.value,
                domainResponse.content.value,
                domainResponse.likeCount.value,
                domainResponse.repostedCount.value,
                JpaPostId(domainResponse.targetId.value)
            )
        }

        fun from(jpaResponse: JpaResponseToPost): ExistingResponse {
            return Response.ExistingResponse(
                MessageId.ResponseId(jpaResponse.id),
                ResponderAccountId(jpaResponse.responderAccountId),
                Content(jpaResponse.content),
                MessageId.PostId(jpaResponse.postId.value),
                LikeCount(jpaResponse.likeCount),
                RepostedCount(jpaResponse.repostedCount)
            )
        }

        fun from(jpaResponses: List<JpaResponseToPost>): ExistingResponses {
            return jpaResponses
                .map { from(it) }
                .let(::ExistingResponses)
        }
    }
}