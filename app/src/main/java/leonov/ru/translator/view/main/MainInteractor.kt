package leonov.ru.translator.view.main

import leonov.ru.model.data.DataModel
import leonov.ru.model.data.SearchResult
import leonov.ru.model.entity.TranslateResult
import ru.leonov.repository.repository.Repository
import ru.leonov.repository.repository.RepositoryLocal
import ru.leonov.repository.room.HistoryEntity
import leonov.ru.translator.utils.convertTranslationToCommaString
import leonov.ru.translator.utils.getPartOfSpeech
import leonov.ru.core.viewmodel.Interactor

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
            localRepository.saveToDB(mapToHistoryEntity(word, translateResultList))

        } else {
            localRepository
                .getData(word).map {
                    mapHistoryEntityToTranslateResult(it, translateResultList)
                }
        }

        return DataModel.Success(translateResultList)
    }

    private fun mapHistoryEntityToTranslateResult(
        historyEntity: HistoryEntity,
        translateResultList: ArrayList<TranslateResult>
    ) {
        translateResultList.add(
            TranslateResult(
                historyEntity.word,
                historyEntity.translation,
                "",
                "",
                "",
                "",
                historyEntity.transcription,
                ""
            )
        )
    }

    private fun mapToHistoryEntity(
        word: String,
        translateResultList: ArrayList<TranslateResult>
    ): HistoryEntity {
        return HistoryEntity(
            word,
            translateResultList.convertTranslationToCommaString(),
            ""
        )
    }


    private fun mapSearchResultToTranslateResult(
        searchResult: SearchResult,
        translateResultList: MutableCollection<TranslateResult>
    ) {
        searchResult.meanings?.let { meaningList ->
            meaningList.forEach { meaning ->
                val partOfSpeech = meaning.partOfSpeechCode?.getPartOfSpeech() ?: ""

                translateResultList.add(
                    TranslateResult(
                        searchResult.text ?: "",
                        meaning.translation?.text ?: "",
                        meaning.translation?.note ?: "",
                        partOfSpeech,
                        meaning.previewUrl ?: "",
                        meaning.imageUrl ?: "",
                        meaning.transcription ?: "",
                        meaning.soundUrl ?: ""
                    )
                )
            }
        }
    }
}
