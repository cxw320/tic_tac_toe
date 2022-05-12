package com.example.tic_tac_toe

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf

class GameViewModel {

    val gameBoardMap = mutableStateMapOf<Int,Int>()

    val currentPlayer = mutableStateOf(Player.ONE)

    val winner = mutableStateOf("Welcome to TicTacToe")


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

        val scoreHashMap = mutableMapOf<String,Int>()

        scoreHashMap.put("ROW1",0)
        scoreHashMap.put("ROW2",0)
        scoreHashMap.put("ROW3",0)
        scoreHashMap.put("COL1",0)
        scoreHashMap.put("COL2",0)
        scoreHashMap.put("COL3",0)
        scoreHashMap.put("DIAG1",0)
        scoreHashMap.put("DIAG2",0)

        for(i in 0..gameBoardMap.size){
          //  Log.d("Caroline", gameBoardMap[i].toString())
            when(i){
                in 0..2 -> scoreHashMap.put(
                    "ROW1",
                    scoreHashMap["ROW1"]?.plus(gameBoardMap[i]!!) ?: 0
                )
                in 3..5 -> scoreHashMap.put(
                    "ROW2",
                    scoreHashMap["ROW2"]?.plus(gameBoardMap[i]!!) ?: 0
                )
                in 6..8 -> scoreHashMap.put(
                    "ROW3",
                    scoreHashMap["ROW3"]?.plus(gameBoardMap[i]!!) ?: 0
                )
            }
            when(i){
                in arrayListOf(0,3,6) -> scoreHashMap.put("COL1",
                    scoreHashMap["COL1"]?.plus(gameBoardMap[i]!!) ?: 0
                )
                in arrayListOf(1,4,7) -> scoreHashMap.put("COL2",
                    scoreHashMap["COL2"]?.plus(gameBoardMap[i]!!) ?: 0
                )
                in arrayListOf(2,5,8) -> scoreHashMap.put("COL3",
                    scoreHashMap["COL3"]?.plus(gameBoardMap[i]!!) ?: 0
                )
            }
            when(i){
                in arrayListOf(0,4,8) -> scoreHashMap.put("DIAG1",
                    scoreHashMap["DIAG1"]?.plus(gameBoardMap[i]!!) ?: 0
                )
                in arrayListOf(2,4,6) -> scoreHashMap.put("DIAG2",
                    scoreHashMap["DIAG2"]?.plus(gameBoardMap[i]!!) ?: 0
                )
            }


            val winnerIndex = scoreHashMap.filter{score->score.value==3||score.value==-3}
            Log.d("Caroline","winner index is ${winnerIndex.keys}")
            if (winnerIndex.isNotEmpty()){
                when(currentPlayer.value){
                    Player.ONE -> winner.value="Game Over: Player One has won"
                    Player.TWO -> winner.value="Game Over: Player Two has won"
                }
            }
        }
    }

}

enum class Player {
    ONE,
    TWO
}
