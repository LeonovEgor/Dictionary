package leonov.ru.translator.di

import leonov.ru.translator.model.data.SearchResult
import leonov.ru.translator.model.datasource.RetrofitImplementation
import leonov.ru.translator.model.datasource.RoomDataBaseImplementation
import leonov.ru.translator.model.repository.Repository
import leonov.ru.translator.model.repository.RepositoryImplementation
import leonov.ru.translator.view.main.MainInteractor
import leonov.ru.translator.view.main.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<Repository<List<SearchResult>>>(named(NAME_REMOTE)) { RepositoryImplementation(RetrofitImplementation()) }
    single<Repository<List<SearchResult>>>(named(NAME_LOCAL)) { RepositoryImplementation(RoomDataBaseImplementation()) }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}