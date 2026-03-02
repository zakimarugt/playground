package learning.ddd.tweet.post.domain.exception

class ResourceNotFoundException(val errorMessage: String, val errorCode: ErrorCode): RuntimeException(errorMessage) {
}