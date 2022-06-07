package com.example.api.model

import java.util.*

data class GameSession(
    val sessionId: Int?,
    val playerId1: Int?,
    val playerId2: Int?,
    val gameBoardMapState: Dictionary<String,Int>?
)