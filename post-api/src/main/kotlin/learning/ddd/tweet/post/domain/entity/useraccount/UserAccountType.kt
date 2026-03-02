package learning.ddd.tweet.post.domain.entity.useraccount

enum class UserAccountType {
    PERSONAL,
    COMPANY;

    companion object {
        fun from(value: String): UserAccountType {
            if (value.equals("PERSONAL")) {
                return PERSONAL
            }

            return COMPANY
        }
    }
}