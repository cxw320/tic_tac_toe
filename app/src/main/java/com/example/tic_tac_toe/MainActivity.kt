package com.example.tic_tac_toe


import android.graphics.Paint
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.text.Layout
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tic_tac_toe.ui.theme.Tic_tac_toeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tic_tac_toeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.ui.graphics.Color.LightGray
                ) {
                    gameBoard()
                }
            }
        }
    }
}

val tileValues = listOf(
    TileData.B,
    TileData.B,TileData.B,TileData.B ,
    TileData.B,TileData.B,TileData.B,
    TileData.B,TileData.B)

enum class TileData{
    X,
    O,
    B
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun gameBoard(){
    LazyVerticalGrid(
        cells = GridCells.Fixed(3)
    ){
        items(tileValues.size){
            Column(
                verticalArrangement = Arrangement.Center
            ){
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .size(140.dp)
                        .background(Color.White)
                ) {
                    TileButton(tileValues[it])
                }
            }
        }
    }
}

@Composable
fun TileButton(tileValue: TileData){
    val tileState = remember {mutableStateOf(tileValue)}

    OutlinedButton(
        onClick = {tileState.value = TileData.X}
    ){
        Text(
            text = tileState.value.toString(),
            textAlign = TextAlign.Center,
            fontSize = 90.sp
        )
    }
}
