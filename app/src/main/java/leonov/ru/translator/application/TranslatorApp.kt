package leonov.ru.translator.application

import android.app.Application
import leonov.ru.translator.di.application
import leonov.ru.translator.di.mainScreen
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}
