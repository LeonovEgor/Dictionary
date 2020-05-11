package leonov.ru.translator.view.main

import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import io.reactivex.rxjava3.core.Observable
import leonov.ru.translator.model.data.DataModel
import leonov.ru.translator.model.repository.RepositoryImplementation
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MainInteractorTest {
    private val local = false
    private val remote = true

    private val mockLocalRepository =  mock(RepositoryImplementation::class.java)
    private val mockRemoteRepository = mock(RepositoryImplementation::class.java)
    private val interactor: Interactor<DataModel> = MainInteractor(mockRemoteRepository, mockLocalRepository)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `get data from local storage`() {

        `when`(mockLocalRepository.getData(ArgumentMatchers.anyString())).thenReturn(
            Observable.just(searchResultList))

        interactor.getData(anyString(), local)

        verify(mockLocalRepository, description("must be call getData from local repository")).getData(anyString())
        verify(mockRemoteRepository, never().description("mustn't be call getData from remote repository")).getData(anyString())
    }

    @Test
    fun `get data from remote storage`() {

        `when`(mockRemoteRepository.getData(anyString())).thenReturn(
            Observable.just(searchResultList))

        interactor.getData(anyString(), remote)

        verify(mockRemoteRepository, description("must be call getData from remote repository")).getData(anyString())
        verify(mockLocalRepository, never().description("mustn't be call getData from local repository")).getData(anyString())
    }

}