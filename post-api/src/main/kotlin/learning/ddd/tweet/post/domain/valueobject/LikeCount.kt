package learning.ddd.tweet.post.domain.valueobject

class LikeCount(val value: Int): ValueObject {
    init {
        if (value.compareTo(0) < 0) {
            throw Exception("Value has to be more than zero.")
        }
    }
}