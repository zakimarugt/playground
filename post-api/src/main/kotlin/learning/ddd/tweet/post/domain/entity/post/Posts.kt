package learning.ddd.tweet.post.domain.entity.post

interface Posts
typealias NewlyCreatedPost = Post.NewlyCreatedPost
typealias ExistingPost = Post.ExistingPost

data class NewlyCreatedPosts (val values: List<NewlyCreatedPost>): Posts

data class ExistingPosts(val values: List<ExistingPost>): Posts {
    fun size(): Int {
        return values.size
    }
}