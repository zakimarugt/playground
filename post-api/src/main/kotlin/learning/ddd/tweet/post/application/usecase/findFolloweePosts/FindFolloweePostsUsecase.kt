package learning.ddd.tweet.post.application.usecase.findFolloweePosts

import learning.ddd.tweet.post.domain.entity.post.ExistingPosts
import learning.ddd.tweet.post.domain.repository.FolloweeRepository
import learning.ddd.tweet.post.domain.repository.PostRepository
import learning.ddd.tweet.post.domain.repository.UserAccountRepository
import learning.ddd.tweet.post.domain.specification.post.FolloweePostsSpecification
import learning.ddd.tweet.post.domain.valueobject.UserAccountId
import org.springframework.stereotype.Component

@Component
class FindFolloweePostsUsecase(
    private val userAccountRepository: UserAccountRepository,
    private val postRepository: PostRepository,
    private val followeeRepository: FolloweeRepository) {

    fun execute(userAccountId: UserAccountId): ExistingPosts {
        // TODO: userAccountの有無, followeeIdsが空の場合は空を返す
        // ユーザーアカウントの存在で、post-dbを使うのはどうなの？
        // val userAccount = userAccountRepository.findById(userAccountId)
        val followeeIds = followeeRepository.findById(userAccountId)

        return followeeIds.takeIf { it.isNotEmpty() }
            ?.let { FolloweePostsSpecification(followeeIds).satisfyingFrom(postRepository) }
            ?: ExistingPosts(listOf())
    }
}