package leonov.ru.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import leonov.ru.model.data.DataModel

abstract class BaseViewModel<T : DataModel>(
    protected open val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)

        } )

    abstract fun getData(word: String, isOnline: Boolean)
    abstract fun handleError(error: Throwable)

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
}
