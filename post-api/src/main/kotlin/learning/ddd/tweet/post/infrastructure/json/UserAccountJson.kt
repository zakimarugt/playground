package learning.ddd.tweet.post.infrastructure.json

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserAccountJson(val id: String, val type: String)