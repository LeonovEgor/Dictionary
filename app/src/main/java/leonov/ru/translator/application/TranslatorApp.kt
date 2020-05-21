package leonov.ru.translator.application

import android.app.Application
import leonov.ru.translator.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(database, repository, mainScreen, historyScreen))
        }
    }
}
