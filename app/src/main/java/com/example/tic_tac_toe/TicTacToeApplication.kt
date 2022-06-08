package com.example.tic_tac_toe

import android.app.Application
import com.example.api.ApiKoinModule
import com.example.repository.RepositoryKoinModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin

import org.koin.dsl.module

class TicTacToeApplication: Application() {
    private val appModule = module{
        viewModel<GameViewModel>()
    }

    override fun onCreate(){
        super.onCreate()
        startKoin {
            modules(
                listOf(
                    appModule,
                    ApiKoinModule.networkModule,
                    RepositoryKoinModule.repositoryModule
                )
            )
        }
    }
}