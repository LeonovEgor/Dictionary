package ru.leonov.repository.repository

interface Repository<T> {

    suspend fun getData(word: String): T
}
