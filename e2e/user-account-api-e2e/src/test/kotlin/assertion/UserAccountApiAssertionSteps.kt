package assertion

import Configuration
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.thoughtworks.gauge.Step
import com.thoughtworks.gauge.datastore.ScenarioDataStore
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserAccountApiAssertionSteps {
    private val httpClient = OkHttpClient()
    private val host = Configuration.ofUserAccountApi()["host"]
    private val port = Configuration.ofUserAccountApi()["port"]
    private val baseUrl = "http://$host:$port"

    @Step("パス<path>にリクエストを送信する")
    fun 指定されたパスにリクエストを送信する(path: String) {
        val request = Request.Builder().url("$baseUrl$path").build()
        val response = httpClient.newCall(request).execute()

        ScenarioDataStore.put("statusCode", response.code)
        ScenarioDataStore.put("body", response.body.string())
    }

    @Step("パス<path>にボディ<filePath>ヘッダー<headers>でリクエストを送信する")
    fun 指定されたパスにボディとヘッダー付きでリクエストを送信する(path: String, filePath: String, headers: String) {
        val body = ObjectMapper().readValue(File(filePath), object : TypeReference<Map<String, Any?>>() {})
            .let { ObjectMapper().writeValueAsString(it) }
        val request = Request.Builder().url("$baseUrl$path").post(body.toRequestBody(headers.toMediaType())).build()
        val response = httpClient.newCall(request).execute()

        ScenarioDataStore.put("statusCode", response.code)
        ScenarioDataStore.put("body", response.body.string())
    }
}