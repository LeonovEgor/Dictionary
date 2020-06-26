package leonov.ru.translator.view.main

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
import leonov.ru.model.data.DataModel
import ru.leonov.repository.repository.RepositoryImplementation
import ru.leonov.repository.repository.RepositoryImplementationLocal
import leonov.ru.translator.view.main.data.historyEntityList
import leonov.ru.translator.view.main.data.searchResultList
import leonov.ru.core.viewmodel.Interactor
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

@ExperimentalCoroutinesApi
class MainInteractorTestCoroutines {

    private val local = false
    private val remote = true

    @MockK
    private lateinit var mockLocalRepository: RepositoryImplementationLocal

    @MockK
    private lateinit var mockRemoteRepository: RepositoryImplementation

    private lateinit var interactor: Interactor<DataModel>

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        interactor = MainInteractor(mockRemoteRepository, mockLocalRepository)

        coEvery { mockLocalRepository.getData(anyString()) } returns historyEntityList
        coEvery { mockRemoteRepository.getData(anyString()) } returns searchResultList
        coEvery { mockLocalRepository.saveToDB(any()) } returns Unit
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `get data from local storage`() = testDispatcher.runBlockingTest {

        interactor.getData(anyString(), local)

        coVerify { mockLocalRepository.getData(anyString()) }
        coVerify(inverse = true) { mockRemoteRepository.getData(anyString()) }
    }

    @Test
    fun `get data from remote storage`() = testDispatcher.runBlockingTest {

        interactor.getData(anyString(), remote)

        coVerify { mockRemoteRepository.getData(anyString()) }
        coVerify(inverse = true) { mockLocalRepository.getData(anyString()) }
    }

}