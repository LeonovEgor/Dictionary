package leonov.ru.translator.model.repository

import io.reactivex.rxjava3.core.Observable

interface Repository<T> {

    fun getData(word: String): Observable<T>
}
