package leonov.ru.translator.view.main

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import leonov.ru.translator.model.data.DataModel
import leonov.ru.translator.viewmodel.BaseViewModel

class MainViewModel (private val interactor: MainInteractor) :
    BaseViewModel<DataModel>() {

    fun subscribe(): LiveData<DataModel> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe(doOnSubscribe())
                .subscribeWith(getObserver())
        )
    }

    private fun doOnSubscribe(): (Disposable) -> Unit =
        { liveDataForViewToObserve.value = DataModel.Loading(null) }

    private fun getObserver(): DisposableObserver<DataModel> {
        return object : DisposableObserver<DataModel>() {

            override fun onNext(data: DataModel) {
                liveDataForViewToObserve.value = data
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = DataModel.Error(e)
            }

            override fun onComplete() {
            }
        }
    }
}
