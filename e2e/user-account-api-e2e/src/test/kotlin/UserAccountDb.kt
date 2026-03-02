import org.dbunit.JdbcDatabaseTester
import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.csv.CsvDataSet
import org.dbunit.operation.DatabaseOperation
import java.io.File

object UserAccountDb {
    val configuration = Configuration.ofUserAccountDb()

    val urlPrefix = configuration.getValue("urlPrefix")
    val driver = configuration.getValue("driver")
    val host = configuration.getValue("host")
    val port = configuration.getValue("port")
    val database = configuration.getValue("database")
    val username = configuration.getValue("username")
    val password = configuration.getValue("password")
    val connectionUrl = "${urlPrefix}${host}:${port}/${database}"

    val connection = JdbcDatabaseTester(driver, connectionUrl, username, password).connection

    fun currentData(): IDataSet {
        return connection.createDataSet()
    }

    fun cleanInsert(dataSetDirectory: File) {
        DatabaseOperation.CLEAN_INSERT.execute(connection, CsvDataSet(dataSetDirectory))
    }
}