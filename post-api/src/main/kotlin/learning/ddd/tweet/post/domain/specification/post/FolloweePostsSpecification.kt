package learning.ddd.tweet.post.domain.specification.post

import learning.ddd.tweet.post.domain.entity.post.ExistingPosts
import learning.ddd.tweet.post.domain.repository.PostRepository
import learning.ddd.tweet.post.domain.valueobject.UserAccountIds

data class FolloweePostsSpecification(val userAccountIds: UserAccountIds) {
    fun satisfyingFrom(postRepository: PostRepository): ExistingPosts {
        return postRepository.searchWith(this)
    }
}
