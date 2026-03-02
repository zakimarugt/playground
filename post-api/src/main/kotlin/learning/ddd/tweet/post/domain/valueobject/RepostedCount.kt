package learning.ddd.tweet.post.domain.valueobject

class RepostedCount(val value: Int): ValueObject {
    init {
        if (value.compareTo(0) < 0) {
            throw Exception("Value must not be null and has to be more than zero.")
        }
    }
}