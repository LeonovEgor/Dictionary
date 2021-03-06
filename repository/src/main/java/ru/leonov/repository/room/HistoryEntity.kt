package ru.leonov.repository.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class HistoryEntity (
    @PrimaryKey var word: String,
    var translation: String,
    val transcription: String
)