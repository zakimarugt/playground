package learning.ddd.tweet.post.application.usecase.deletepost.dto

import learning.ddd.tweet.post.domain.valueobject.MessageId
import learning.ddd.tweet.post.domain.valueobject.UserAccountId

class DeletePostInputDto(val userAccountId: UserAccountId, val postId: MessageId.PostId)