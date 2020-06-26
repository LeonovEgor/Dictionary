package ru.leonov.history.utils

import leonov.ru.model.entity.TranslateResult
import ru.leonov.repository.room.HistoryEntity

fun HistoryEntity.mapToTranslateResult(): TranslateResult =
    TranslateResult(
        this.word,
        this.translation,
        "",
        "",
        "",
        "",
        this.transcription,
        ""
    )
