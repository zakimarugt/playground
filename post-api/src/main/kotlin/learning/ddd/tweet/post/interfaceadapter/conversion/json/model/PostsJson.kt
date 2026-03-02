package learning.ddd.tweet.post.interfaceadapter.conversion.json.model

import com.fasterxml.jackson.annotation.JsonProperty

data class PostsJson(@JsonProperty("posts") val values: List<PostJson>)

data class PostJson(val postId: String, val content: String)