package leonov.ru.translator.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import leonov.ru.translator.application.TranslatorApp
import javax.inject.Singleton


@Component(
    modules = [
        RepositoryModule::class,
        InteractorModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        AndroidSupportInjectionModule::class]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(translatorApp: TranslatorApp)
}
