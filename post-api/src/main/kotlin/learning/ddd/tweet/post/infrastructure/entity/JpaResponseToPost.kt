package learning.ddd.tweet.post.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "responses")
data class JpaResponseToPost(
    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "responder_account_id")
    val responderAccountId: String,

    @Column(name = "content")
    val content: String,

    @Column(name = "like_count")
    val likeCount: Int,

    @Column(name = "reposted_count")
    val repostedCount: Int,

    @ManyToOne
    @JoinTable(
        name = "response_to_post",
        joinColumns = [JoinColumn(name = "from_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "to_id", referencedColumnName = "id")]
    )
    val postId: JpaPostId
)