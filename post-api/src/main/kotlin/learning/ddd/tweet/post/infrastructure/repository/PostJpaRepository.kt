package learning.ddd.tweet.post.infrastructure.repository

import learning.ddd.tweet.post.domain.entity.post.ExistingPosts
import learning.ddd.tweet.post.domain.entity.post.IDeleatablePostId
import learning.ddd.tweet.post.domain.entity.post.Post
import learning.ddd.tweet.post.domain.repository.PostRepository
import learning.ddd.tweet.post.domain.specification.post.FolloweePostsSpecification
import learning.ddd.tweet.post.domain.specification.post.UserAccountPostsSpecification
import learning.ddd.tweet.post.domain.valueobject.MessageId
import learning.ddd.tweet.post.domain.valueobject.UserAccountId
import learning.ddd.tweet.post.infrastructure.entity.JpaPost
import learning.ddd.tweet.post.interfaceadapter.conversion.database.PostEntityConverter
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Repository

typealias NewlyCreatedPost = Post.NewlyCreatedPost
typealias ExistingPost = Post.ExistingPost

@Repository
class PostJpaRepository(private val postRepository: IPostJpaRepository): PostRepository {
    override fun save(post: Post) {
        postRepository.save(PostEntityConverter.fromDomainEntity(post))
    }

    override fun findById(id: MessageId.PostId): ExistingPost {
        val jpaPost = postRepository.findById(id.value).get()
        return PostEntityConverter.fromJpaEntity(jpaPost)
    }

    override fun findBy(userAccountId: UserAccountId): ExistingPosts {
        return postRepository.findByUserAccountIdIs(userAccountId.value)
            .map { PostEntityConverter.fromJpaEntities(it) }
            .get()
    }

    override fun deleteById(id: IDeleatablePostId) {
        postRepository.deleteById(id.value)
    }

    override fun searchWith(specification: FolloweePostsSpecification): ExistingPosts {
        val jpaPosts = postRepository.findAll(specification.toJpaSpecification())
        return PostEntityConverter.fromJpaEntities(jpaPosts)
    }

    override fun searchWith(specification: UserAccountPostsSpecification): ExistingPosts {
        val jpaPosts = postRepository.findAll(specification.toJpaSpecification())

        return PostEntityConverter.fromJpaEntities(jpaPosts)
    }
}

private fun FolloweePostsSpecification.toJpaSpecification(): Specification<JpaPost> {
    return Specification<JpaPost> { root, query, criteriaBuilder ->
        root.get<String>("userAccountId").`in`(this.userAccountIds.values.map { it.value })
    }
}

private fun UserAccountPostsSpecification.toJpaSpecification(): Specification<JpaPost> {
    return Specification<JpaPost> { root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<String>("userAccountId"), this.userAccountId.value)
    }
}