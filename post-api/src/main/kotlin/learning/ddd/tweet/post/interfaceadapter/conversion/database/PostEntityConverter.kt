package learning.ddd.tweet.post.interfaceadapter.conversion.database

import learning.ddd.tweet.post.domain.entity.post.*
import learning.ddd.tweet.post.domain.valueobject.*
import learning.ddd.tweet.post.infrastructure.entity.JpaPost

typealias NewlyCreatedPost = Post.NewlyCreatedPost
typealias ExistingPost = Post.ExistingPost

class PostEntityConverter {
    companion object {
        fun fromJpaEntity(jpaPost: JpaPost): ExistingPost {
            return ExistingPost(
                MessageId.PostId(jpaPost.id),
                Content(jpaPost.content),
                RepostedCount(jpaPost.repostedCount),
                LikeCount(jpaPost.likeCount),
                UserAccountId(jpaPost.userAccountId)
            )
        }

        fun fromDomainEntity(domainPost: Post): JpaPost {
            return JpaPost(
                domainPost.id.value,
                domainPost.content.value,
                domainPost.repostedCount.value,
                domainPost.likeCount.value,
                domainPost.userAccountId.value
            )
        }

        fun fromJpaEntities(jpaPosts: List<JpaPost>): ExistingPosts {
            return jpaPosts
                .map { jpaPost -> fromJpaEntity(jpaPost) }
                .let(::ExistingPosts)
        }
    }
}