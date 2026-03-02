package learning.ddd.tweet.post.domain.entity.response

typealias NewlyCreatedResponse = Response.NewlyCreatedResponse
typealias ExistingResponse = Response.ExistingResponse

interface Responses {
    val values: List<Response>
}

data class NewlyCreatedResponses(override val values: List<NewlyCreatedResponse>): Responses

data class ExistingResponses(override val values: List<Response.ExistingResponse>): Responses


