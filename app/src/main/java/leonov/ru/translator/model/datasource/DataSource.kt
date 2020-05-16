package leonov.ru.translator.model.datasource


interface DataSource<T> {

    suspend fun getData(word: String): T
}
