package learning.ddd.tweet.post.domain.valueobject.followee

data class FolloweeId(val value: String)

data class FolloweeIds(val values: List<FolloweeId>)