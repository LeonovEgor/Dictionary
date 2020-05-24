package ru.leonov.history.view

import leonov.ru.model.data.DataModel
import leonov.ru.model.data.SearchResult
import ru.leonov.repository.repository.Repository
import ru.leonov.repository.repository.RepositoryLocal
import ru.leonov.repository.room.HistoryEntity
import leonov.ru.core.viewmodel.Interactor
import ru.leonov.history.utils.mapToTranslateResult

class HistoryInteractor(
    private val repositoryRemote: Repository<List<SearchResult>>,
    private val repositoryLocal: RepositoryLocal<List<HistoryEntity>>
) : Interactor<DataModel> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel {
        return DataModel.Success(
            if (fromRemoteSource) {
                // Пока непонятно, какой тут будет функционал.
                listOf()
            } else {
                repositoryLocal.getData(word).map { it.mapToTranslateResult() }
            }
        )
    }
}