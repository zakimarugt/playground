package assertion

import UserAccountDb
import com.thoughtworks.gauge.Step
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.dbunit.Assertion
import org.dbunit.assertion.comparer.value.ValueComparer
import org.dbunit.dataset.ReplacementDataSet
import org.dbunit.dataset.csv.CsvDataSet
import org.dbunit.dataset.filter.DefaultColumnFilter
import java.io.File
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime

class UserAccountDbAssertionSteps {
    private val database = UserAccountDb

    @Step("テーブルの状態が<expectedFilesDirectory>の内容に一致する")
    fun 指定されたテーブルの状態が意図する状態となっている(expectedDataFilesDirectory: String) {
        val expected = ReplacementDataSet(CsvDataSet(File(expectedDataFilesDirectory)))
        expected.addReplacementObject(
            "now",
            Timestamp.from(
                ZonedDateTime.now(ZoneId.of("UTC")).toInstant()
            )
        )

        val actual = database.currentData()

        val excludedColumns = arrayOf<String>("expired_date", "value")
        val excludedTable = "verification_tokens"
        val filteredExpectedDataSet = DefaultColumnFilter.excludedColumnsTable(expected.getTable(excludedTable), excludedColumns)
        val filteredActualDataSet = DefaultColumnFilter.excludedColumnsTable(actual.getTable(excludedTable), excludedColumns)
        Assertion.assertEquals(filteredExpectedDataSet, filteredActualDataSet)
    }
}