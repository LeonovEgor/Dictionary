package leonov.ru.model.data

import leonov.ru.model.entity.TranslateResult

sealed class DataModel {

    data class Success(val data: List<TranslateResult>) : DataModel()
    data class Error(val error: Throwable) : DataModel()
    data class Loading(val progress: Int?) : DataModel()
}
