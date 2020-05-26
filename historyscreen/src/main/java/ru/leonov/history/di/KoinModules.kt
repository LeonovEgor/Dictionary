package ru.leonov.history.di

import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import ru.leonov.history.view.HistoryInteractor
import ru.leonov.history.view.HistoryViewModel

fun injectDependencies() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}