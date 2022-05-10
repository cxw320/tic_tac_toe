package com.example.tic_tac_toe

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf

class GameViewModel {

    val gameBoardMap = mutableStateMapOf<Int,Int>()

    val currentPlayer = mutableStateOf(Player.ONE)

    var row1Score = 0
    var row2Score = 0
    var row3Score = 0
    var col1Score = 0
    var col2Score = 0
    var col3Score = 0
    var diag1Score = 0
    var diag2Score = 0

    init{
        for(i in 0.. 8){
            gameBoardMap[i] = 0
        }
    }

    fun updateCurrentPlayer(){
        if(currentPlayer.value == Player.ONE){
            currentPlayer.value = Player.TWO
        }else{
            currentPlayer.value = Player.ONE
        }
    }

    fun updateGameTile(gameBoardIndex:Int){
        if(currentPlayer.value==Player.ONE){
            gameBoardMap[gameBoardIndex] = 1
        }else{
            gameBoardMap[gameBoardIndex] = -1
        }
    }

    fun checkForWinningBoard(){

        for(i in 0..gameBoardMap.size){
            Log.d("Caroline", gameBoardMap[i].toString())
            when(i){
                in 0..2 -> row1Score = row1Score + gameBoardMap[i]!!
                in 3..5 -> row2Score += gameBoardMap[i]!!
                in 6..8 -> row3Score += gameBoardMap[i]!!
                in arrayListOf(0,3,6) -> col1Score += gameBoardMap[i]!!
                in arrayListOf(1,4,7) -> col2Score += gameBoardMap[i]!!
                in arrayListOf(2,5,8) -> col3Score += gameBoardMap[i]!!
            }
        }
        Log.d("Caroline", "Score is $row1Score")


    }

}

enum class Player {
    ONE,
    TWO
}
