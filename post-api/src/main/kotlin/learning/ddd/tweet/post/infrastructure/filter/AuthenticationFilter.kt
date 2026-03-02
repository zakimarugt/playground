package learning.ddd.tweet.post.infrastructure.filter

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(2)
class AuthenticationFilter {
//    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
//        return chain.filter(exchange).transformDeferred{ call -> doBeforeRequest(exchange, call) }
//    }
//
//    // TODO: Mono<Void>を返すようにしないといけないためonErrorResumeを末尾に持ってきている
//    private fun doBeforeRequest(exchange: ServerWebExchange, call: Mono<Void>): Publisher<Void> {
//        return Mono.fromCallable { validateJwt(exchange) }
//            .then(call)
//            .onErrorResume { throwable -> handleJwtValidationError(exchange, throwable) }
//    }
//
//    private fun validateJwt(exchange: ServerWebExchange) {
//        val secret: Hash = Hash()
//
//        val secretKey: String = secret.getSecretKey()
//        val algorithm: Algorithm = Algorithm.HMAC256(secretKey)
//
//        val verifier: JWTVerifier = JWT.require(algorithm)
//            .withIssuer(secret.getTokenIssuer())
//            .withSubject(secret.getTokenSubject())
//            .build()
//
//       verifier.verify(exchange.request.headers.get("Authorization")?.get(0)?.replace("\"", ""))
//    }
//
//    private fun handleJwtValidationError(exchange: ServerWebExchange, throwable: Throwable): Mono<Void> {
//        exchange.response.statusCode = HttpStatusCode.valueOf(401)
//        exchange.response.headers.set("Content-Type", "application/json")
//
//        val dataBuffer: DataBuffer = exchange.response.bufferFactory().wrap(
//            String.format("{\"error\": \"%s\"}", throwable.message).toByteArray()
//        )
//
//        return exchange.response.writeWith(Mono.just(dataBuffer))
//    }
}
