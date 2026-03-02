package learning.ddd.tweet.post.infrastructure.driver

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.serialization.json.Json
import learning.ddd.tweet.post.infrastructure.http.HttpClient
import learning.ddd.tweet.post.infrastructure.json.FolloweeIdsJson
import learning.ddd.tweet.post.infrastructure.json.UserAccountJson
import org.springframework.stereotype.Component

@Component
class UserAccountApiDriver(private val httpClient: HttpClient) {
    companion object {
        private val basePath = "http://localhost:8082/userAccounts/"
    }

    fun findFolloweeIds(userAccountId: String): FolloweeIdsJson {
        val url = basePath.plus("${userAccountId}/followees/ids")
        return httpClient.get(url).body?.string()
            ?.let { Json.decodeFromString<FolloweeIdsJson>(it) }
            ?: throw Exception("")
    }

    fun findBy(userAccountId: String): UserAccountJson {
        val url = basePath.plus(userAccountId)
        return httpClient.get(url).body?.string()
            ?.let { body -> jacksonObjectMapper().readValue<UserAccountJson>(body) }
            ?: throw Exception("")
    }
}
