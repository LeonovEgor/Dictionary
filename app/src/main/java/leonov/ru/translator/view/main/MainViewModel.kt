package leonov.ru.translator.view.main

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import leonov.ru.translator.model.data.DataModel
import leonov.ru.translator.viewmodel.BaseViewModel

class MainViewModel (private val interactor: MainInteractor) :
    BaseViewModel<DataModel>() {

    fun subscribe(): LiveData<DataModel> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        liveDataForViewToObserve.value = DataModel.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        withContext(Dispatchers.IO) {
            liveDataForViewToObserve.postValue(interactor.getData(word, isOnline))
        }
    }

    override fun handleError(error: Throwable) {
        liveDataForViewToObserve.postValue(DataModel.Error(error))
    }

}