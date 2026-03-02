import com.thoughtworks.gauge.BeforeScenario
import com.thoughtworks.gauge.BeforeSuite
import com.thoughtworks.gauge.ExecutionContext
import java.io.File

class Setup {
    companion object {
        var userAccountDb: UserAccountDb? = null
    }

    @BeforeSuite
    fun beforeSuite() {
        userAccountDb = UserAccountDb
    }

    @BeforeScenario
    fun beforeScenario(executionContext: ExecutionContext) {
        val specificationPath = executionContext.currentSpecification.fileName.substringAfter("specs/").substringBefore(".spec")

        userAccountDb!!.cleanInsert(File("src/test/resources/${specificationPath}/pre-condition"))
    }
}