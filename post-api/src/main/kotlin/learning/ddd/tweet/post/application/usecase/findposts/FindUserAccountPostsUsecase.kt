package learning.ddd.tweet.post.application.usecase.findposts

import learning.ddd.tweet.post.domain.entity.post.ExistingPosts
import learning.ddd.tweet.post.domain.repository.PostRepository
import learning.ddd.tweet.post.domain.repository.UserAccountRepository
import learning.ddd.tweet.post.domain.specification.post.FolloweePostsSpecification
import learning.ddd.tweet.post.domain.specification.post.UserAccountPostsSpecification
import learning.ddd.tweet.post.domain.valueobject.UserAccountId
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class FindUserAccountPostsUsecase(private val userAccountRepository: UserAccountRepository, private val postRepository: PostRepository) {
    suspend fun execute(userAccountId: UserAccountId): ExistingPosts {
//        val userAccount = userAccountRepository.findById(userAccountId)

        val specification = UserAccountPostsSpecification(userAccountId)

        return postRepository.searchWith(specification)
    }
}