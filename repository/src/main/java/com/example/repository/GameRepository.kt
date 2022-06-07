package com.example.repository

import com.example.api.model.GameSession

interface GameRepository {

    suspend fun getGameSession():Result<GameSession>
}