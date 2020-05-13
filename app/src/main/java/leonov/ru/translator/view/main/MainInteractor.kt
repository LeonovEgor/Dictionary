package leonov.ru.translator.view.main

import io.reactivex.rxjava3.core.Observable
import leonov.ru.translator.viewmodel.Interactor
import leonov.ru.translator.model.data.DataModel
import leonov.ru.translator.model.data.SearchResult
import leonov.ru.translator.model.repository.Repository
import leonov.ru.translator.di.NAME_LOCAL
import leonov.ru.translator.di.NAME_REMOTE
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: Repository<List<SearchResult>>,
    @Named(NAME_LOCAL) val localRepository: Repository<List<SearchResult>>
) : Interactor<DataModel> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<DataModel> {
        return if (fromRemoteSource) {
            remoteRepository
        } else {
            localRepository
        }.getData(word).map { DataModel.Success(it) }
    }
}
