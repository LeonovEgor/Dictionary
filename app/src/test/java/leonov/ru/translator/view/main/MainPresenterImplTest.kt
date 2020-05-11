package leonov.ru.translator.view.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import leonov.ru.translator.model.data.DataModel
import leonov.ru.translator.view.base.View
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class MainPresenterImplTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockInteractor = mock(MainInteractor::class.java)
    private val mockView = mock(View::class.java)

    private val presenter = MainPresenterImpl<DataModel, View>(mockInteractor, CompositeDisposable(), SchedulerProviderForTest())

    private val mockDataModelSuccess = mockk<DataModel.Success>()
    private val mockDataModelError = mockk<DataModel.Error>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        // Другой вариант
        //RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        presenter.attachView(mockView)
    }

    @After
    fun tearDown() {
        presenter.detachView(mockView)
    }

    @Test
    fun `getData should return translation data`()  {

        `when`(mockInteractor.getData( anyString(), anyBoolean() ) )
            .thenReturn(Observable.just(mockDataModelSuccess))

        presenter.getData(anyString(), anyBoolean())

        verify(mockView).renderData(mockDataModelSuccess)
        verify(mockView, never()).renderData(mockDataModelError)
    }

    @Test
    fun `getData should return Error data`()  {

        `when`(mockInteractor.getData( anyString(), anyBoolean() ) )
            .thenReturn(Observable.just(mockDataModelError))

        presenter.getData(anyString(), anyBoolean())

        verify(mockView).renderData(mockDataModelError)
        verify(mockView, never()).renderData(mockDataModelSuccess)
    }

}