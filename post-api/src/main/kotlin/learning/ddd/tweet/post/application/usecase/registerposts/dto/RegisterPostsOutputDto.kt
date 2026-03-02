package learning.ddd.tweet.post.application.usecase.registerposts.dto

import learning.ddd.tweet.post.domain.valueobject.MessageId

// TODO: ユーザーの全ポストを返却する必要はないと思われる
data class RegisterPostsOutputDto(val newlyCreatedPostId: MessageId.PostId)