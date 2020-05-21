package leonov.ru.translator.di

import androidx.room.Room
import leonov.ru.translator.model.data.SearchResult
import leonov.ru.translator.model.datasource.RetrofitImplementation
import leonov.ru.translator.model.datasource.RoomDataBaseImplementation
import leonov.ru.translator.model.repository.Repository
import leonov.ru.translator.model.repository.RepositoryImplementation
import leonov.ru.translator.model.repository.RepositoryImplementationLocal
import leonov.ru.translator.model.repository.RepositoryLocal
import leonov.ru.translator.room.HistoryDataBase
import leonov.ru.translator.room.HistoryEntity
import leonov.ru.translator.view.history.HistoryInteractor
import leonov.ru.translator.view.history.HistoryViewModel
import leonov.ru.translator.view.main.MainInteractor
import leonov.ru.translator.view.main.MainViewModel
import org.koin.dsl.module

//val application = module {
//    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
//    single { get<HistoryDataBase>().historyDao() }
//    single<Repository<List<SearchResult>>> { RepositoryImplementation(RetrofitImplementation()) }
//    single<RepositoryLocal<List<HistoryEntity>>> { RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
//    }
//}

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

val mainScreen = module {
    factory { MainInteractor(get(), get()) }
    factory { MainViewModel(get()) }
}

val historyScreen = module {
    factory { HistoryInteractor(get(), get()) }
    factory { HistoryViewModel(get()) }
}