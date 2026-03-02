package learning.ddd.tweet.post.application.usecase.registerposts.dto

import learning.ddd.tweet.post.domain.valueobject.Content
import learning.ddd.tweet.post.domain.valueobject.UserAccountId

data class RegisterPostsInputDto(val userAccountId: UserAccountId, val content: Content) {
    constructor(userAccountId: String, content: String): this(
        UserAccountId(userAccountId), Content(content)
    )
}