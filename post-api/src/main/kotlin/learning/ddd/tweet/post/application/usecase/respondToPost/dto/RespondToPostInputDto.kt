package learning.ddd.tweet.post.application.usecase.respondToPost.dto

import learning.ddd.tweet.post.domain.entity.response.ResponderAccountId
import learning.ddd.tweet.post.domain.valueobject.Content
import learning.ddd.tweet.post.domain.valueobject.MessageId
import learning.ddd.tweet.post.domain.valueobject.UserAccountId

data class RespondToPostInputDto(
    val responderAccountId: ResponderAccountId,
    val responseContent: Content,
    val userAccountId: UserAccountId,
    val postId: MessageId.PostId
)