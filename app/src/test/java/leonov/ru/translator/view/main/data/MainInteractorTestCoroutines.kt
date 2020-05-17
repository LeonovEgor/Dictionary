package leonov.ru.translator.view.main.data

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import leonov.ru.translator.model.data.DataModel
import leonov.ru.translator.model.repository.RepositoryImplementation
import leonov.ru.translator.view.main.MainInteractor
import leonov.ru.translator.viewmodel.Interactor
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

@ExperimentalCoroutinesApi
class MainInteractorTestCoroutines {

    private val local = false
    private val remote = true

    @MockK
    private lateinit var mockLocalRepository: RepositoryImplementation

    @MockK
    private lateinit var mockRemoteRepository: RepositoryImplementation

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `get data from local storage`() = testDispatcher.runBlockingTest {

        val interactor: Interactor<DataModel> =
            MainInteractor(mockRemoteRepository, mockLocalRepository)

        coEvery { mockLocalRepository.getData(anyString()) } returns searchResultList
        coEvery { mockRemoteRepository.getData(anyString()) } returns searchResultList

        interactor.getData(anyString(), local)

        coVerify { mockLocalRepository.getData(anyString()) }
        coVerify(inverse = true) { mockRemoteRepository.getData(anyString()) }
    }

    @Test
    fun `get data from remote storage`() = testDispatcher.runBlockingTest {

        val interactor: Interactor<DataModel> =
            MainInteractor(mockRemoteRepository, mockLocalRepository)

        coEvery { mockLocalRepository.getData(anyString()) } returns searchResultList
        coEvery { mockRemoteRepository.getData(anyString()) } returns searchResultList

        interactor.getData(anyString(), remote)

        coVerify { mockRemoteRepository.getData(anyString()) }
        coVerify(inverse = true) { mockLocalRepository.getData(anyString()) }
    }

}