package leonov.ru.translator.model.datasource

import leonov.ru.translator.room.HistoryDao
import leonov.ru.translator.room.HistoryEntity

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<HistoryEntity>> {

    override suspend fun getData(word: String): List<HistoryEntity> =
        if (word.isEmpty()) historyDao.getAll()
        else historyDao.getByWord(word)

    override suspend fun saveToDB(history: HistoryEntity) = historyDao.insert(history)
}