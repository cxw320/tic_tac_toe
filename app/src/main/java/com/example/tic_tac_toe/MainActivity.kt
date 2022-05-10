package com.example.tic_tac_toe


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tic_tac_toe.ui.theme.Tic_tac_toeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gameViewModel = GameViewModel()

        setContent {
            Tic_tac_toeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.ui.graphics.Color.LightGray
                ) {
                    gameBoard(gameViewModel)
                }
            }
        }
    }
}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun gameBoard(gameViewModel: GameViewModel){
    var currentPlayer = remember {mutableStateOf(Player.ONE)}

    Column(

    ){
        LazyVerticalGrid(
            cells = GridCells.Fixed(3)
        ){
            items(gameViewModel.gameBoardMap.size){
                Column(
                    verticalArrangement = Arrangement.Center
                ){
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .size(140.dp)
                            .background(Color.White)
                    ) {
                        TileButton(it,gameViewModel)

                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(50.dp)
                .border(5.dp, color = Color.Black)
                .align(Alignment.CenterHorizontally)
        ){
            Text(
                text="Game Over: Player ${currentPlayer.value} won",
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth()
                )
        }
    }
}

@Composable
fun TileButton(gameBoardIndex: Int, gameViewModel: GameViewModel){
   // val tileState = remember {mutableStateOf(gameVi)}
    OutlinedButton(
        onClick = {
            gameViewModel.apply{
                updateGameTile(gameBoardIndex)
                updateCurrentPlayer()
                checkForWinningBoard()
            }
        }
    ){
        Text(
            text = when(gameViewModel.gameBoardMap[gameBoardIndex]){
                       1 -> "X"
                       -1 -> "O"
                       0 -> ""
                       else ->""
                   },
            textAlign = TextAlign.Center,
            fontSize = 90.sp
        )
    }
}
