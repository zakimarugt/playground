package learning.ddd.tweet.post.application.usecase.deletepost

import learning.ddd.tweet.post.application.usecase.deletepost.dto.DeletePostInputDto
import learning.ddd.tweet.post.domain.repository.PostRepository
import learning.ddd.tweet.post.domain.repository.ResponseRepository
import learning.ddd.tweet.post.domain.repository.UserAccountRepository
import org.springframework.stereotype.Component

@Component
class DeletePostUsecase(
    val userAccountRepository: UserAccountRepository,
    val postRepository: PostRepository,
    val responseRepository: ResponseRepository) {
    suspend fun execute(inputDto: DeletePostInputDto) {
        val userAccount = userAccountRepository.findById(inputDto.userAccountId)
        val post = postRepository.findById(inputDto.postId)

        val deletablePostId = post.delete(userAccount.id)

        postRepository.deleteById(deletablePostId)

    }
}