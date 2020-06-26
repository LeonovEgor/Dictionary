package leonov.ru.translator.koin

import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

class DependencyGraphTest: KoinTest {

    private val serviceName = "service_name"
    @Test
    fun testFunc() {
        val mockModule = module {
            single(named(serviceName)) { SampleService() }
        }

        startKoin{
            modules(listOf(mockModule))
        }

        val service: SampleService = get(named(serviceName))

        assertNotNull(service)
    }

}