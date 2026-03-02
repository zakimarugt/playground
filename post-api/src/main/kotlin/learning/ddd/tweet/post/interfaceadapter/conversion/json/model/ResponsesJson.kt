package learning.ddd.tweet.post.interfaceadapter.conversion.json.model

data class ResponsesJson(val targetId: String, val responses: List<ResponseJson>)

data class ResponseJson(
    val id: String,
    val responderAccountId: String,
    val content: String,
    val likeCount: Int,
    val repostedCount: Int
)