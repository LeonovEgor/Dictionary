package leonov.ru.translator.model.datasource

import leonov.ru.translator.room.HistoryEntity

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(history: HistoryEntity)
}
