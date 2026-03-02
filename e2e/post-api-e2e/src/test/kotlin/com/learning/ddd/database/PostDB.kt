package com.learning.ddd.database

import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import com.thoughtworks.gauge.Step
import org.dbunit.Assertion
import org.dbunit.dataset.csv.CsvDataSet
import java.io.File

class PostDB {
    private val database = Database(
        Key("post.db.driver", stringType),
        Key("post.db.url", stringType),
        Key("post.db.username", stringType),
        Key("post.db.password", stringType)
    )

    fun initializeDataFor(specificationPath: String) {
        val dataPath = "src/test/resources/com/learning/ddd/${specificationPath}/post-db/"
        val data = CsvDataSet(File(dataPath))

        database.cleanInsertWith(data)
    }

    @Step("ポストの内容が<filePath>の状態で登録されている")
    fun ポストの内容が登録されている(filePath: String) {
        val currentDatabaseSnapshot = database.currentDatabaseSnapshot()

        val ignorableColumnsForPostRegistration = mapOf("posts" to listOf("id"))

        ignorableColumnsForPostRegistration.forEach { (tableName, columnName) ->
            val actual = currentDatabaseSnapshot.getTable(tableName)
            val expected = CsvDataSet(File(filePath)).getTable(tableName)

            Assertion.assertEqualsIgnoreCols(expected, actual, columnName.toTypedArray())
        }
    }

    @Step("レスポンスの内容が<filePath>の状態で登録されている")
    fun レスポンスの内容が登録されている(filePath: String) {
        val ignorableColumnsForResponseRegistration = mapOf(
            "responses" to listOf("id"),
            "response_to_post" to listOf("id", "from_id")
        )

        val currentDatabaseSnapshot = database.currentDatabaseSnapshot()

        ignorableColumnsForResponseRegistration.forEach { (tableName, columnName) ->
            val actual = currentDatabaseSnapshot.getTable(tableName)
            val expected = CsvDataSet(File(filePath)).getTable(tableName)

            Assertion.assertEqualsIgnoreCols(expected, actual, columnName.toTypedArray())
        }


    }
}