package learning.ddd.tweet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TweetApplication

fun main(args: Array<String>) {
	runApplication<TweetApplication>(*args)
}
