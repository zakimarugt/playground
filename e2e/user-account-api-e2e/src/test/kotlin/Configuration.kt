import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

object Configuration {
    val values = ObjectMapper(YAMLFactory()).readValue(File("src/test/resources/properties.yaml"), object: TypeReference<Map<String, Map<String, String>>>() {})

    fun ofUserAccountApi(): Map<String,String> {
        return values.getValue("userAccountApi")
    }

    fun ofUserAccountDb(): Map<String, String> {
        return values.getValue("userAccountDb")
    }
}
