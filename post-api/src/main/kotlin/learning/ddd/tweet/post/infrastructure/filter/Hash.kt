package learning.ddd.tweet.post.infrastructure.filter

class Hash {
    companion object {
        val secretKey: String = "lkhapsjf[jtpqu959qy34jlroqhefa"
        val tokenIssuer: String = "your_name"
        val tokenSubject: String = "service_token"
    }

    fun getSecretKey(): String {
        return secretKey
    }

    fun getTokenIssuer(): String {
        return tokenIssuer
    }

    fun getTokenSubject(): String {
        return tokenSubject
    }
}