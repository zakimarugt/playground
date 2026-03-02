package learning.ddd.tweet.post.infrastructure.json

import kotlinx.serialization.Serializable

@Serializable
data class FolloweeIdsJson(val followeeIds: List<String>)