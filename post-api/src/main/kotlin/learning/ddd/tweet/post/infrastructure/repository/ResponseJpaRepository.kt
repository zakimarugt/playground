package learning.ddd.tweet.post.infrastructure.repository

import learning.ddd.tweet.post.domain.entity.response.ExistingResponses
import learning.ddd.tweet.post.domain.entity.response.Response
import learning.ddd.tweet.post.domain.repository.ResponseRepository
import learning.ddd.tweet.post.domain.valueobject.MessageId
import learning.ddd.tweet.post.infrastructure.entity.JpaPostId
import learning.ddd.tweet.post.infrastructure.entity.JpaResponseId
import learning.ddd.tweet.post.infrastructure.entity.JpaResponseToPost
import learning.ddd.tweet.post.infrastructure.entity.JpaResponseToResponse
import learning.ddd.tweet.post.interfaceadapter.conversion.database.ResponseToPostConverter
import learning.ddd.tweet.post.interfaceadapter.conversion.database.ResponseToResponseConverter
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Repository

typealias ExistingResponse = Response.ExistingResponse

@Repository
class ResponseJpaRepository(
    private val responseToPostRepository: IResponseToPostJpaRepository,
    private val responseToResponseRepository: IResponseToResponseJpaRepository
): ResponseRepository {
    override fun findBy(targetId: MessageId): ExistingResponses {
        return when (targetId) {
            is MessageId.PostId -> {
                val specification = Specification<JpaResponseToPost> { root, query, builder ->
                builder.equal(root.get<JpaPostId>("postId"), JpaPostId(targetId.value))
                }
                responseToPostRepository.findAll(specification).let { it -> ResponseToPostConverter.from(it) }
            }

            is MessageId.ResponseId -> {
                val specification = Specification<JpaResponseToResponse> { root, query, builder ->
                    builder.equal(root.get<JpaResponseId>("responseId"), JpaResponseId(targetId.value))
                }
                responseToResponseRepository.findAll(specification).let { ResponseToResponseConverter.from(it) }
            }
        }
    }

    override fun save(response: Response) {
        when (response.targetId) {
            is MessageId.PostId -> {responseToPostRepository.save(ResponseToPostConverter.from(response))}
            is MessageId.ResponseId -> {responseToResponseRepository.save(ResponseToResponseConverter.from(response))}
        }
    }
}
