package com.example.api

import com.example.api.model.GameSession
import com.example.api.networking.NetworkResult
import retrofit2.http.GET

interface TicTacToeApi {

    @GET("GameSession")
    suspend fun getGameSession() : NetworkResult<GameSession>

}