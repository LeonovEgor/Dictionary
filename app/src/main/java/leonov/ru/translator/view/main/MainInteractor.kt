package leonov.ru.translator.view.main

import leonov.ru.translator.viewmodel.Interactor
import leonov.ru.translator.model.data.DataModel
import leonov.ru.translator.model.data.SearchResult
import leonov.ru.translator.model.repository.Repository
import leonov.ru.translator.model.entity.TranslateResult

class MainInteractor (
    private val remoteRepository: Repository<List<SearchResult>>,
    private val localRepository: Repository<List<SearchResult>>
) : Interactor<DataModel> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel {
        val translateResultList = ArrayList<TranslateResult>()
        if (fromRemoteSource) {
            remoteRepository
        } else {
            localRepository
        }.getData(word)
            .map { mapToTranslateResult(it, translateResultList) }

        return DataModel.Success(translateResultList)
    }

    private fun mapToTranslateResult(searchResult: SearchResult, translateResultList: MutableCollection<TranslateResult>) {
        searchResult.meanings?.let { meaningList->
            meaningList.forEach {meaning->
                translateResultList.add(
                    TranslateResult(
                    searchResult.text ?: "",
                    meaning.translation?.text ?: "",
                        meaning.partOfSpeechCode ?: "",
                        meaning.previewUrl ?: "",
                        meaning.transcription ?: "",
                        meaning.soundUrl ?: ""
                    )
                )
            }
        }
    }
}
