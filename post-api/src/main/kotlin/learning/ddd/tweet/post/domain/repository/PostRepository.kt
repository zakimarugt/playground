package learning.ddd.tweet.post.domain.repository

import learning.ddd.tweet.post.domain.entity.post.ExistingPosts
import learning.ddd.tweet.post.domain.entity.post.IDeleatablePostId
import learning.ddd.tweet.post.domain.entity.post.Post
import learning.ddd.tweet.post.domain.specification.post.FolloweePostsSpecification
import learning.ddd.tweet.post.domain.specification.post.UserAccountPostsSpecification
import learning.ddd.tweet.post.domain.valueobject.MessageId
import learning.ddd.tweet.post.domain.valueobject.UserAccountId

typealias NewlyCreatedPost = Post.NewlyCreatedPost
typealias ExistingPost = Post.ExistingPost

interface PostRepository {
    fun findById(id: MessageId.PostId): ExistingPost
    fun findBy(userAccountId: UserAccountId): ExistingPosts
    fun save(post: Post)
    fun deleteById(id: IDeleatablePostId)
    fun searchWith(specification: FolloweePostsSpecification): ExistingPosts
    fun searchWith(specification: UserAccountPostsSpecification): ExistingPosts
}