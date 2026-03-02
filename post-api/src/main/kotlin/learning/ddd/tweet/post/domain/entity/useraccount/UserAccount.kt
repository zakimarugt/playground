package learning.ddd.tweet.post.domain.entity.useraccount

import learning.ddd.tweet.post.domain.entity.post.Post
import learning.ddd.tweet.post.domain.valueobject.UserAccountId

typealias NewlyCreatedPost = Post.NewlyCreatedPost

sealed class UserAccount(open val id: UserAccountId) {
    data class PersonalAccount(override val id: UserAccountId): UserAccount(id)
    data class CompanyAccount(override val id: UserAccountId): UserAccount(id)
}
