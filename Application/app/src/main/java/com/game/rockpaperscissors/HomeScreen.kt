package com.game.rockpaperscissors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game.rockpaperscissors.ui.theme.Oswald


@Preview
@Composable
fun HomeScreen (){


    val modes: List<Int> = listOf(132,15,132,42,323,1,12,2)
    val favoriteMods: List<Int> = listOf(1,32,4)


    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = "Game Statistics",
                fontSize = 20.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = Color.White
            )
        }
        LazyColumn(
            modifier = Modifier.padding(top = 10.dp)
        ){
            items(3){
                DisplayMods()
            }

            item{

                val size = 85
                Row(
                    modifier = Modifier.padding(25.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Box(
                        modifier = Modifier
                            .width(size.dp)
                            .height(size.dp)
                            .background(MaterialTheme.colorScheme.onBackground)
                    ){

                    }

                    Box(
                        modifier = Modifier
                            .width(size.dp)
                            .height(size.dp)
                            .background(MaterialTheme.colorScheme.onBackground)
                    ){

                    }

                    Box(
                        modifier = Modifier
                            .width(size.dp)
                            .height(size.dp)
                            .background(MaterialTheme.colorScheme.onBackground)
                    ){

                    }
                }
            }

            items(9){
                DisplayMods()
            }
        }
    }
}

@Composable
fun DisplayMods() {
    Box(modifier = Modifier.fillMaxWidth()
        .height(100.dp)
        .padding(start = 25.dp, end = 25.dp, top = 10.dp)
        .background(MaterialTheme.colorScheme.secondary)){

    }
}


















