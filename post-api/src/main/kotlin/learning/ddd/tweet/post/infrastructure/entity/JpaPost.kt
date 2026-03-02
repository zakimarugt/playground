package learning.ddd.tweet.post.infrastructure.entity

import jakarta.persistence.*


@Entity
@Table(name = "posts")
@NamedEntityGraph(name = "post.graph", includeAllAttributes = true)
class JpaPost(
    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "content")
    val content: String,

    @Column(name = "reposted_count")
    val repostedCount: Int,

    @Column(name = "like_count")
    val likeCount: Int,

    @Column(name = "user_id")
    val userAccountId: String
)