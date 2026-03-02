package learning.ddd.tweet.post.application.usecase.registerposts

import learning.ddd.tweet.post.application.usecase.registerposts.dto.RegisterPostsInputDto
import learning.ddd.tweet.post.domain.entity.post.Post
import learning.ddd.tweet.post.domain.repository.PostRepository
import learning.ddd.tweet.post.domain.repository.UserAccountRepository
import learning.ddd.tweet.post.domain.valueobject.Content
import learning.ddd.tweet.post.domain.valueobject.UserAccountId
import org.springframework.stereotype.Component


@Component
class RegisterPostsUseCase(
    private val userAccountRepository: UserAccountRepository, private val postRepository: PostRepository) {
    fun execute(inputDto: RegisterPostsInputDto) {
        val userAccount = userAccountRepository.findById(inputDto.userAccountId)
        val existingPosts = postRepository.findBy(inputDto.userAccountId)

        // postの総数を取得する必要があるが、クライアントに意識させるのは違う気もする
        val newlyCreatedPost = Post.NewlyCreatedPost.create(userAccount,inputDto.content, existingPosts.size())

        postRepository.save(newlyCreatedPost)
    }
}