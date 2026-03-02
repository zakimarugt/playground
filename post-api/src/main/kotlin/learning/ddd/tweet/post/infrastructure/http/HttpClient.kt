package learning.ddd.tweet.post.infrastructure.http

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.stereotype.Component

@Component
class HttpClient(private val okHttpClient: OkHttpClient) {
    fun get(url: String): Response {
        val request = Request.Builder()
            .url(url)
            .build()

        return okHttpClient.newCall(request).execute()
    }
}