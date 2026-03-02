package learning.ddd.tweet.post.application.usecase.respondToPost

import learning.ddd.tweet.post.application.usecase.respondToPost.dto.RespondToPostInputDto
import learning.ddd.tweet.post.domain.entity.response.NewlyCreatedResponse
import learning.ddd.tweet.post.domain.repository.PostRepository
import learning.ddd.tweet.post.domain.repository.ResponseRepository
import learning.ddd.tweet.post.domain.repository.UserAccountRepository
import org.springframework.stereotype.Component

@Component
class RespondToPostUsecase(
    private val userAccountRepository: UserAccountRepository,
    private val postRepository: PostRepository,
    private val responseRepository: ResponseRepository) {

    fun execute(inputDto: RespondToPostInputDto) {
        val userAccount = userAccountRepository.findById(inputDto.userAccountId)
        val post = postRepository.findById(inputDto.postId)

        val response = NewlyCreatedResponse.create(inputDto.responderAccountId, inputDto.responseContent, inputDto.postId)

        responseRepository.save(response)
    }
}