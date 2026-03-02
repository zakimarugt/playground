package learning.ddd.tweet.post.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "posts")
data class JpaPostId(
    @Id
    @Column(name = "id")
    val value: String
)