package ru.leonov.repository.datasource

import ru.leonov.repository.room.HistoryDao
import ru.leonov.repository.room.HistoryEntity

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<HistoryEntity>> {

    override suspend fun getData(word: String): List<HistoryEntity> =
        if (word.isEmpty()) historyDao.getAll()
        else historyDao.getByWord(word)

    override suspend fun saveToDB(history: HistoryEntity) = historyDao.insert(history)
}