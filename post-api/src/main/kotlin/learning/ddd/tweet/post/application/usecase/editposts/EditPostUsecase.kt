package learning.ddd.tweet.post.application.usecase.editposts

import learning.ddd.tweet.post.application.usecase.editposts.dto.EditPostInputDto
import learning.ddd.tweet.post.domain.repository.PostRepository
import learning.ddd.tweet.post.domain.repository.UserAccountRepository
import learning.ddd.tweet.post.domain.valueobject.Content
import learning.ddd.tweet.post.domain.valueobject.MessageId
import learning.ddd.tweet.post.domain.valueobject.UserAccountId
import org.springframework.stereotype.Component

@Component
class EditPostUsecase(private val userAccountRepository: UserAccountRepository, private val postRepository: PostRepository) {
    fun execute(inputDto: EditPostInputDto) {
        val userAccount = userAccountRepository.findById(UserAccountId(inputDto.userAccountId))
        val post = postRepository.findById(MessageId.PostId(inputDto.postId))
        val editedPost = post.edit(Content(inputDto.content))

        postRepository.save(editedPost)
    }
}