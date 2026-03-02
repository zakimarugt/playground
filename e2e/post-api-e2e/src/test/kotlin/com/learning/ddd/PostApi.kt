package com.learning.ddd

import com.learning.ddd.response.ResponseAssertion
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.intType
import com.natpryce.konfig.stringType
import com.thoughtworks.gauge.Step
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class PostApi {
    private val httpClient = OkHttpClient()
    private val host = Key("post-api.host", stringType)
    private val port = Key("post-api.port", intType)
    private val property = ConfigurationProperties.fromResource("test.properties")

    @Step("フォローしているユーザーアカウントのポストを全て取得するエンドポイントにリクエストする")
    fun フォローしているユーザーアカウントのポストを全て取得するエンドポイントにリクエストする() {
        val request = Request.Builder()
            .url("http://${property[host]}:${property[port]}/userAccounts/12345/followees/posts")
            .build()

        httpClient.newCall(request).execute().use { response ->
            ResponseAssertion.storeStatusCode(response.code)
            ResponseAssertion.storeJsonResponse(response.body?.string())
        }
    }

    @Step("ユーザーアカウントIDが<value>のポストを全て取得するエンドポイントにリクエストする")
    fun 指定したユーザーアカウントのポストを全て取得するエンドポイントにリクエストする(value: String) {
        val request = Request.Builder()
            .url("http://${property[host]}:${property[port]}/userAccounts/${value}/posts")
            .build()

        httpClient.newCall(request).execute().use { response ->
            ResponseAssertion.storeStatusCode(response.code)
            ResponseAssertion.storeJsonResponse(response.body?.string())
        }
    }

    @Step("ユーザーアカウントIDが<userAccountId>のユーザーアカウントで、ポストを1件投稿するエンドポイントに<filePath>の内容でリクエストする")
    fun 指定したユーザーアカウントでポストを1件投稿するエンドポイントにリクエストする(userAccountId: String, filePath: String) {
        val request = Request.Builder()
            .url("http://${property[host]}:${property[port]}/userAccounts/${userAccountId}/posts")
            .post(File(filePath).asRequestBody("application/json".toMediaType()))
            .build()

        httpClient.newCall(request).execute().use { response ->
            ResponseAssertion.storeStatusCode(response.code)
        }
    }

    @Step("ユーザーアカウントIDが<userAccountId>のユーザーアカウントで、ポストIDが<postId>のポストを修正するエンドポイントに<filePath>の内容でリクエストする")
    fun 指定したユーザーアカウントでポストを修正するエンドポイントにリクエストする(userAccountId: String, postId: String, filePath: String) {
        val request = Request.Builder()
            .url("http://${property[host]}:${property[port]}/userAccounts/${userAccountId}/posts/${postId}")
            .patch(File(filePath).asRequestBody("application/json".toMediaType()))
            .build()

        httpClient.newCall(request).execute().use { response ->
            ResponseAssertion.storeStatusCode(response.code)
        }
    }

    @Step("ユーザーアカウントIDが<userAccountId>のユーザーアカウントで、ポストIDが<postId>のポストを削除するエンドポイントに<filePath>の内容でリクエストする")
    fun 指定したユーザーアカウントでポストを削除するエンドポイントにリクエストする(userAccountId: String, postId: String, filePath: String) {
        val request = Request.Builder()
            .url("http://${property[host]}:${property[port]}/userAccounts/${userAccountId}/posts/${postId}")
            .delete()
            .build()

        httpClient.newCall(request).execute().use { response ->
            ResponseAssertion.storeStatusCode(response.code)
        }
    }

    @Step("ユーザーアカウントIDが<userAccountId>のユーザーが投稿した、ポストIDが<postId>のポストに対してレスポンスを投稿するエンドポイントに<filePath>の内容でリクエストする")
    fun 指定したユーザーアカウントで指定したポストに対してレスポンスを投稿するエンドポイントにリクエストする(userAccountId: String, postId: String, filePath: String) {
        val request = Request.Builder()
            .url("http://${property[host]}:${property[port]}/userAccounts/${userAccountId}/posts/${postId}/responses")
            .post(File(filePath).asRequestBody("application/json".toMediaType()))
            .build()

        httpClient.newCall(request).execute().use { response ->
            ResponseAssertion.storeStatusCode(response.code)
        }
    }

    @Step("ユーザーアカウントIDが<userAccountId>のユーザーが投稿した、ポストIDが<postId>のポストに対してレスポンスを取得するエンドポイントにリクエストする")
    fun 指定したユーザーアカウントが投稿したポストに対するレスポンスを取得するエンドポイントにリクエストする(userAccountId: String, postId: String) {
        val request = Request.Builder()
            .url("http://${property[host]}:${property[port]}/userAccounts/${userAccountId}/posts/${postId}/responses")
            .build()

        httpClient.newCall(request).execute().use { response ->
            ResponseAssertion.storeStatusCode(response.code)
            ResponseAssertion.storeJsonResponse(response.body?.string())
        }
    }
}
