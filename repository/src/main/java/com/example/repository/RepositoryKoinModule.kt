package com.example.repository

import org.koin.dsl.module

object RepositoryKoinModule {
    val repositoryModule = module{
        single<GameRepository>{GameRepositoryImpl(get())}
    }
}