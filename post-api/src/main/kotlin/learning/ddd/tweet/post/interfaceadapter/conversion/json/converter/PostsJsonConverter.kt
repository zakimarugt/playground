package learning.ddd.tweet.post.interfaceadapter.conversion.json.converter

import learning.ddd.tweet.post.domain.entity.post.ExistingPosts
import learning.ddd.tweet.post.interfaceadapter.conversion.json.model.PostJson
import learning.ddd.tweet.post.interfaceadapter.conversion.json.model.PostsJson


class PostsJsonConverter {
    companion object {
        fun toJson(posts: ExistingPosts): PostsJson {
            return posts.values
                .map { post -> PostJson(post.id.value, post.content.value) }
                .let(::PostsJson)
        }
    }
}