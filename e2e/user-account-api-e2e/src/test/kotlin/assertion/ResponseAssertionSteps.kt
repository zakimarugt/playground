package assertion

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.thoughtworks.gauge.Step
import com.thoughtworks.gauge.datastore.ScenarioDataStore
import org.amshove.kluent.shouldBeEqualTo
import java.io.File

class ResponseAssertionSteps {
    @Step("レスポンスのステータスコードが<value>である")
    fun レスポンスのステータスコードが指定された値である(value: Int) {
        ScenarioDataStore.get("statusCode") shouldBeEqualTo value
    }

    @Step("レスポンスのボディが<filePath>の内容に一致する")
    fun レスポンスのボディが指定されたファイルの内容に一致する(filePath: String) {
        val expectedResponseBody = ObjectMapper().readValue(File(filePath), object: TypeReference<Map<String, Any?>>() {})
        val actualResponseBody = ObjectMapper().readValue(ScenarioDataStore.get("body").toString(), object: TypeReference<Map<String, Any?>>() {})

        actualResponseBody shouldBeEqualTo expectedResponseBody
    }
}