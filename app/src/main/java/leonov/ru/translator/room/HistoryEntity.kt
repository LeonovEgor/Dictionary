package leonov.ru.translator.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class HistoryEntity (
    @PrimaryKey var word: String,
    var translation: String,
    val transcription: String
)