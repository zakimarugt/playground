package learning.ddd.tweet.post.domain.entity.post

sealed interface IDeleatablePostId {
    val value: String
}