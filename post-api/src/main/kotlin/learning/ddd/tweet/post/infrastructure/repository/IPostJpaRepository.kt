package learning.ddd.tweet.post.infrastructure.repository

import learning.ddd.tweet.post.infrastructure.entity.JpaPost
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface IPostJpaRepository: JpaRepository<JpaPost, String>, JpaSpecificationExecutor<JpaPost> {
    @EntityGraph(value = "post.graph", type = EntityGraph.EntityGraphType.FETCH)
    override fun findById(id: String): Optional<JpaPost>

    fun findByUserAccountIdIs(userAccountId: String): Optional<List<JpaPost>>
}