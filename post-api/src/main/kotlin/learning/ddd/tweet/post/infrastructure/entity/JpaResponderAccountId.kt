package learning.ddd.tweet.post.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class JpaResponderAccountId(
    @Id
    @Column(name = "id")
    val value: String
)