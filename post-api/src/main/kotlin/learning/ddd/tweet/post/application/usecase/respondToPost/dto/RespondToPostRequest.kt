package learning.ddd.tweet.post.application.usecase.respondToPost.dto

data class RespondToPostRequest(val responderAccountId: String, val responseContent: String)