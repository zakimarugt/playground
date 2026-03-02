package com.learning.ddd.response

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.thoughtworks.gauge.Step
import com.thoughtworks.gauge.datastore.ScenarioDataStore
import org.amshove.kluent.shouldBeEqualTo
import java.io.File

class ResponseAssertion {
    companion object {
        fun storeStatusCode(code: Int) {
            ScenarioDataStore.put("status", code)
        }

        fun storeJsonResponse(value: String?) {
            ScenarioDataStore.put("body", value)
        }

        fun statusCode() = ScenarioDataStore.get("status")
    }

    @Step("レスポンスのステータスが<statusCode>である")
    fun ステータスコードがstatusCodeである(statusCode: Int) {
        statusCode() shouldBeEqualTo statusCode
    }

    @Step("レスポンスのボディがファイルパス<filePath>のjsonと一致している")
    fun レスポンスボディが指定されたファイルパスのjsonと一致している(filePath: String) {
        val expected = jacksonObjectMapper().readValue(File(filePath), object: TypeReference<Map<String, Any?>>() {})
        val actual = jacksonObjectMapper().readValue(ScenarioDataStore.get("body").toString(), object: TypeReference<Map<String, Any?>>() {})

        expected shouldBeEqualTo actual
    }
}
