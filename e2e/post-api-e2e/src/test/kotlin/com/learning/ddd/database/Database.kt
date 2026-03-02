package com.learning.ddd.database

import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import org.dbunit.IDatabaseTester
import org.dbunit.JdbcDatabaseTester
import org.dbunit.database.IDatabaseConnection
import org.dbunit.dataset.IDataSet
import org.dbunit.operation.DatabaseOperation

class Database(
    private val driver: Key<String>,
    private val url: Key<String>,
    private val userName: Key<String>,
    private val password: Key<String>
) {
    private val connection: IDatabaseConnection
    private val property = ConfigurationProperties.fromResource("test.properties")

    init {
        connection =
            JdbcDatabaseTester(property[driver], property[url], property[userName], property[password]).connection
    }

    fun cleanInsertWith(data: IDataSet) {
        DatabaseOperation.CLEAN_INSERT.execute(connection, data)
    }

    fun currentDatabaseSnapshot(): IDataSet {
        return connection.createDataSet()
    }
}