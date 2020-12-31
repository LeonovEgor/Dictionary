package leonov.ru.translator.di

import androidx.room.Room
import leonov.ru.model.data.SearchResult
import leonov.ru.translator.view.main.MainActivity
import ru.leonov.repository.datasource.RetrofitImplementation
import ru.leonov.repository.datasource.RoomDataBaseImplementation
import ru.leonov.repository.repository.Repository
import ru.leonov.repository.repository.RepositoryImplementation
import ru.leonov.repository.repository.RepositoryImplementationLocal
import ru.leonov.repository.repository.RepositoryLocal
import ru.leonov.repository.room.HistoryDataBase
import ru.leonov.repository.room.HistoryEntity
import leonov.ru.translator.view.main.MainInteractor
import leonov.ru.translator.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(repository, database, mainScreen))
}


val repository = module {

    single<Repository<List<SearchResult>>> {
        RepositoryImplementation(RetrofitImplementation())
    }

    single<RepositoryLocal<List<HistoryEntity>>> {
        RepositoryImplementationLocal(
            RoomDataBaseImplementation(get())
        )
    }
}

val database = module {
    single {
        Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB")
            .build()
    }
    single { get<HistoryDataBase>().historyDao() }
}


//val mainScreen = module {
//    scope(named<MainActivity>()) {
//        scoped { MainInteractor(get(), get()) }
//        viewModel { MainViewModel(get()) }
//    }
val mainScreen = module {
    scope<MainActivity> {
        scoped { MainInteractor(get(), get()) }
        viewModel { MainViewModel(get()) }
    }

}