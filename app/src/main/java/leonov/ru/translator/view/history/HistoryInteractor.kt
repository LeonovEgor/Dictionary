package leonov.ru.translator.view.history

import leonov.ru.translator.model.data.DataModel
import leonov.ru.translator.model.data.SearchResult
import leonov.ru.translator.model.entity.TranslateResult
import leonov.ru.translator.model.repository.Repository
import leonov.ru.translator.model.repository.RepositoryLocal
import leonov.ru.translator.room.HistoryEntity
import leonov.ru.translator.viewmodel.Interactor

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
                repositoryLocal.getData(word).map { mapToTranslateResult(it) }
            }
        )
    }

    private fun mapToTranslateResult(history: HistoryEntity): TranslateResult =
        TranslateResult(
            history.word,
            history.translation,
            "",
            "",
            "",
            "",
            history.transcription,
            ""
        )

}
