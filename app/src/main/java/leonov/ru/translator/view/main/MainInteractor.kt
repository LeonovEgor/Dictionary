package leonov.ru.translator.view.main

import io.reactivex.rxjava3.core.Observable
import leonov.ru.translator.viewmodel.Interactor
import leonov.ru.translator.model.data.DataModel
import leonov.ru.translator.model.data.SearchResult
import leonov.ru.translator.model.repository.Repository
import leonov.ru.translator.model.entity.TranslateResult

class MainInteractor (
    private val remoteRepository: Repository<List<SearchResult>>,
    private val localRepository: Repository<List<SearchResult>>
) : Interactor<DataModel> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<DataModel> {
        return if (fromRemoteSource) {
            remoteRepository
        } else {
            localRepository
        }.getData(word).map {searchResultList ->

            val translateResultList = ArrayList<TranslateResult>()
            searchResultList.forEach {searchResult->
                mapToTranslateResult(searchResult, translateResultList)
            }
            DataModel.Success(translateResultList)
        }
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
