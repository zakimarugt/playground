package learning.ddd.tweet.post.domain.valueobject

data class Content(val value: String) {
    companion object {
        fun from(value: String): Content {
            validate(value).getOrThrow()

            return Content(value)
        }

        fun validate(value: String): Result<Unit> {
            if (value.isEmpty() || (value.length > 100)) {
                return Result.failure(IllegalArgumentException("A value should be less than 100 characters."))
            }

            return Result.success(Unit)
        }
    }
}
