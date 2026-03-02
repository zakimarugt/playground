package com.learning.ddd

import com.github.tomakehurst.wiremock.client.WireMock
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.intType
import com.natpryce.konfig.stringType
import java.io.File

class UserAccountApi {
    private val host = Key("user-account-api.host", stringType)
    private val port = Key("user-account-api.port", intType)
    private val property = ConfigurationProperties.fromResource("test.properties")
    private val mock = WireMock(property[host], property[port])

    fun clearMapping() {
        mock.resetMappings()
    }

    fun initializeMappingFor(specificationPath: String) {
        val mappingPath = "src/test/resources/com/learning/ddd/"
            .plus(specificationPath)
            .plus("/userAccountApi")

        File(mappingPath)
            .takeIf { it.exists() }
            ?.let { mock.loadMappingsFrom(it) }
    }
}