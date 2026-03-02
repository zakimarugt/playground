package learning.ddd.tweet.post.domain.specification.post

import learning.ddd.tweet.post.domain.entity.post.ExistingPosts
import learning.ddd.tweet.post.domain.repository.PostRepository
import learning.ddd.tweet.post.domain.valueobject.UserAccountId

data class UserAccountPostsSpecification(val userAccountId: UserAccountId) {
    fun satisfyingFrom(postRepository: PostRepository): ExistingPosts {
        return postRepository.searchWith(this)
    }
}