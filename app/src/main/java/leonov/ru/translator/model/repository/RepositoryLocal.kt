package leonov.ru.translator.model.repository

import leonov.ru.translator.room.HistoryEntity

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(history: HistoryEntity)
}
