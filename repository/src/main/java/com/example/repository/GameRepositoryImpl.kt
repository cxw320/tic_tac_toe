package com.example.repository

import com.example.api.TicTacToeApi
import com.example.api.model.GameSession
import com.example.api.networking.NetworkResult

class GameRepositoryImpl(
    private val ticTacToeApi: TicTacToeApi
) : GameRepository{

    override suspend fun getGameSession(): Result<GameSession> {
        return when (val result = ticTacToeApi.getGameSession()){
            is NetworkResult.Error -> Result.Failure(result.error)
            is NetworkResult.Failure -> Result.Failure(
                Exception(
                    result.throwable.message,
                    result.throwable
                )
            )
            is NetworkResult.Success -> Result.Success(result.body)
        }
    }
}