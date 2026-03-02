package learning.ddd.tweet.post.domain.entity.post

import learning.ddd.tweet.post.domain.entity.useraccount.UserAccount
import learning.ddd.tweet.post.domain.exception.DomainException
import learning.ddd.tweet.post.domain.valueobject.*
import java.util.*

typealias PersonalAccount = UserAccount.PersonalAccount
typealias CompanyAccount = UserAccount.CompanyAccount

sealed class Post(
    val id: MessageId.PostId,
    var content: Content,
    val repostedCount: RepostedCount,
    val likeCount: LikeCount,
    val userAccountId: UserAccountId
) {
    class NewlyCreatedPost private constructor(
        id: MessageId.PostId, content: Content, repostedCount: RepostedCount, likeCount: LikeCount, userAccountId: UserAccountId
    ) : Post(id, content, repostedCount, likeCount, userAccountId) {
        companion object {
            // UserAccountにpostメソッドを持たせるとPostのファクトリメソッドの意味がなくなる
            // 個人用アカウントからの作成/企業用アカウントからの作成でロジックが分かれないのであれば、private constructorにする必要はなさそう
            // Existingのコンストラクタを利用して新規ポストを作成されることも可能性としてある
            fun create(userAccount: UserAccount, content: Content, existingPostCount: Int): NewlyCreatedPost {
                return when (userAccount) {
                    is PersonalAccount -> createPersonalNewPost(userAccount.id, content, existingPostCount)
                    is CompanyAccount -> createCompanyNewPost(userAccount.id, content, existingPostCount)
                }
            }

            private fun createPersonalNewPost(userAccountId: UserAccountId, content: Content, existingPostCount: Int): NewlyCreatedPost {
                if (existingPostCount < 10) {
                    return NewlyCreatedPost(
                        MessageId.PostId(UUID.randomUUID().toString()),
                        content,
                        RepostedCount(0),
                        LikeCount(0),
                        userAccountId
                    )
                } else {
                    throw DomainException("User already created 15 posts.")
                }
            }

            private fun createCompanyNewPost(userAccountId: UserAccountId, content: Content, existingPostCount: Int): NewlyCreatedPost {
                if (existingPostCount < 100) {
                    return NewlyCreatedPost(
                        MessageId.PostId(UUID.randomUUID().toString()),
                        content,
                        RepostedCount(0),
                        LikeCount(0),
                        userAccountId
                    )
                } else {
                    throw DomainException("User already created 15 posts.")
                }
            }
        }
    }

    class ExistingPost(
        id: MessageId.PostId, content: Content, repostedCount: RepostedCount, likeCount: LikeCount, userAccountId: UserAccountId
    ): Post(id, content, repostedCount, likeCount, userAccountId) {
        fun edit(newContent: Content): ExistingPost {
            return this.apply { content = newContent }
        }

        fun delete(
            userAccountId: UserAccountId
        ): IDeleatablePostId {
            return if (userAccountId == this.userAccountId) {
                DeletablePostId(this.id.value)
            } else {
                throw Exception("")
            }
        }
    }
}

private data class DeletablePostId(override val value: String): IDeleatablePostId