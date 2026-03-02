package learning.ddd.tweet.post.application.usecase.findresponses

import learning.ddd.tweet.post.application.usecase.findresponses.dto.FindResponsesInputDto
import learning.ddd.tweet.post.domain.entity.response.ExistingResponses
import learning.ddd.tweet.post.domain.repository.PostRepository
import learning.ddd.tweet.post.domain.repository.ResponseRepository
import learning.ddd.tweet.post.domain.repository.UserAccountRepository
import learning.ddd.tweet.post.domain.valueobject.MessageId
import learning.ddd.tweet.post.domain.valueobject.UserAccountId
import org.springframework.stereotype.Service

@Service
class FindResponsesUsecase(
    private val userAccountRepository: UserAccountRepository,
    private val responseRepository: ResponseRepository,
    private val postRepository: PostRepository) {
    fun execute(inputDto: FindResponsesInputDto): ExistingResponses {
        val postId = MessageId.PostId(inputDto.postId)
        val userAccount = userAccountRepository.findById(UserAccountId(inputDto.userAccountId))
        val post = postRepository.findById(postId)

        return responseRepository.findBy(postId)
    }
}