package learning.ddd.tweet.post.infrastructure.repository

import learning.ddd.tweet.post.infrastructure.entity.JpaResponseToPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface IResponseToPostJpaRepository: JpaRepository<JpaResponseToPost, String>, JpaSpecificationExecutor<JpaResponseToPost>