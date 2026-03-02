package learning.ddd.tweet.post.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.cors.reactive.CorsUtils
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsConfig {
    companion object {
        val ALLOWED_HEADERS: String = "Content-Type, Authorization"
        val ALLOWED_METHODS: String = "GET, POST, PUT, DELETE, OPTIONS"
        val ALLOWED_ORIGINS: String = "http://localhost:4200"
        val MAX_AGE: String = "3600"
    }

    @Bean
    @Order(1)
    fun corsFilter(): WebFilter {
        return WebFilter { exchange: ServerWebExchange, chain: WebFilterChain ->
            val request: ServerHttpRequest = exchange.request
            if (CorsUtils.isCorsRequest(request)) {
                val response: ServerHttpResponse = exchange.response
                val headers: HttpHeaders = response.headers
                headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS)
                headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS)
                headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGINS)
                headers.add("Access-Control-Max-Age", MAX_AGE)

                if (request.method == HttpMethod.OPTIONS) {
                    response.statusCode = HttpStatus.OK
                    return@WebFilter Mono.empty<Void>()
                }
            }
            chain.filter(exchange)
        }
    }
}