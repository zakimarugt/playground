package learning.ddd.tweet.post.application.usecase.editposts.dto

data class EditPostInputDto(val userAccountId: String, val postId: String, val content: String)
