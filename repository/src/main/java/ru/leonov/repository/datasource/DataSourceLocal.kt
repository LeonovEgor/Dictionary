package ru.leonov.repository.datasource

import ru.leonov.repository.room.HistoryEntity

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(history: HistoryEntity)
}
