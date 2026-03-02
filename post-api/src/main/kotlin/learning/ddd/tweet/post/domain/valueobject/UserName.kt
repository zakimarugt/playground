package learning.ddd.tweet.post.domain.valueobject

import learning.ddd.tweet.post.domain.exception.DomainException
import org.springframework.util.ObjectUtils

data class UserName(val value: String) {
    init {
        if (ObjectUtils.isEmpty(value) || value.length == 0) {
            throw DomainException("The length of username must not be more than one.")
        }
    }
}