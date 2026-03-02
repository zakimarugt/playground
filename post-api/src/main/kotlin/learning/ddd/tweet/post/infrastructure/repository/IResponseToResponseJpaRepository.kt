package learning.ddd.tweet.post.infrastructure.repository

import learning.ddd.tweet.post.infrastructure.entity.JpaResponseToResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface IResponseToResponseJpaRepository: JpaRepository<JpaResponseToResponse, String>, JpaSpecificationExecutor<JpaResponseToResponse>