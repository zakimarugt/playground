package learning.ddd.tweet.post.domain.exception

class DomainException(val errorMessage: String): Exception(errorMessage) {
}