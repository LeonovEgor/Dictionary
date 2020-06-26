package leonov.ru.translator.view.main

import leonov.ru.model.data.DataModel
import leonov.ru.model.data.SearchResult
import leonov.ru.model.entity.TranslateResult
import ru.leonov.repository.repository.Repository
import ru.leonov.repository.repository.RepositoryLocal
import ru.leonov.repository.room.HistoryEntity
import leonov.ru.core.viewmodel.Interactor
import leonov.ru.translator.utils.mapHistoryEntityToTranslateResult
import leonov.ru.translator.utils.mapSearchResultToTranslateResult
import leonov.ru.translator.utils.mapTranslateResultListToHistoryEntity

class MainInteractor(
    private val remoteRepository: Repository<List<SearchResult>>,
    private val localRepository: RepositoryLocal<List<HistoryEntity>>
) : Interactor<DataModel> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel {
        val translateResultList = ArrayList<TranslateResult>()
        if (fromRemoteSource) {
            remoteRepository
                .getData(word)
                .map { mapSearchResultToTranslateResult(it, translateResultList) }
            localRepository.saveToDB(mapTranslateResultListToHistoryEntity(word, translateResultList))

        } else {
            localRepository
                .getData(word).map {
                    mapHistoryEntityToTranslateResult(it, translateResultList)
                }
        }

        return DataModel.Success(translateResultList)
    }

}
