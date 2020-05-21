package leonov.ru.translator.model.repository

import leonov.ru.translator.model.datasource.DataSourceLocal
import leonov.ru.translator.room.HistoryEntity

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<HistoryEntity>>) :
    RepositoryLocal<List<HistoryEntity>> {

    override suspend fun getData(word: String): List<HistoryEntity> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(history: HistoryEntity) {
        dataSource.saveToDB(history)
    }
}
