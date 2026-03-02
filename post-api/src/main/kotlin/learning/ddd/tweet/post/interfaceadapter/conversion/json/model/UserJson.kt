package learning.ddd.tweet.post.interfaceadapter.conversion.json.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserJson(
    @JsonProperty("id")
    val id: String
    )