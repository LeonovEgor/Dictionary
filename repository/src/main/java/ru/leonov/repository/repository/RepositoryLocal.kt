package ru.leonov.repository.repository

import ru.leonov.repository.room.HistoryEntity

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(history: HistoryEntity)
}
