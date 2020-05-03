package leonov.ru.translator.presenter

import leonov.ru.translator.model.data.DataModel
import leonov.ru.translator.view.base.View

interface Presenter<T : DataModel, V : View> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}
