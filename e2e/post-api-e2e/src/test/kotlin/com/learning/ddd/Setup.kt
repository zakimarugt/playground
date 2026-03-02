import com.learning.ddd.UserAccountApi
import com.learning.ddd.database.PostDB
import com.thoughtworks.gauge.BeforeSpec
import com.thoughtworks.gauge.ExecutionContext

class Setup {
    var postDB = PostDB()
    var userAccountApi = UserAccountApi()

    @BeforeSpec
    fun initializationBeforeScenario(context: ExecutionContext) {
        val specificationPath = context.currentSpecification.fileName
            .substringAfter("specs/")
            .removeSuffix(".spec")

        postDB.initializeDataFor(specificationPath)
        userAccountApi.initializeMappingFor(specificationPath)
    }
}