package com.game.rockpaperscissors.composable.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.composable.BarGraph
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.viewModel.GameViewModel
import com.game.rockpaperscissors.ui.theme.Oswald


@Composable
fun GameStatisticScreen(
    navController: NavController,
    viewModel: GameViewModel
){

    BoxWithConstraints (modifier = Modifier.fillMaxSize()){
        val lazyHeight = (this.maxHeight - 50.dp) -45.dp

        Column(
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
            modifier = Modifier.height(lazyHeight)
            ) {
                val barName = listOf("Rock", "Paper", "Scissors")
                item {
                    WinStatistic(viewModel)
                }
                item {
                    BarGraph(
                        values = viewModel.playerSelection,
                        modifier = Modifier.padding(28.dp),
                        barPadding = 64.dp,
                        height = 362,
                        name = barName,
                        title = "Your Selection"
                    )
                }
                item {
                    BarGraph(
                        values = viewModel.enemySelection,
                        name = barName,
                        height = 362,
                        barPadding = 64.dp,
                        modifier = Modifier.padding(28.dp),
                        title = "Enemy Selection"
                    )
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(end = 24.dp)
                    .clickable {
                        navController.navigate(Screen.HomeScreen.route)
                    },
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowForwardIos,
                    contentDescription = "Navigation"
                )
            }
        }
    }
}


@Composable
fun WinStatistic(
    gameViewModel: GameViewModel
) {


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        gameViewModel.cWin()
        Text(
            text = if(gameViewModel.isWin.collectAsState().value) "Win" else "Lose",
            fontFamily = Oswald,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = if(gameViewModel.isWin.collectAsState().value) Color.Green else Color.Red
        )

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            val width = this.maxWidth / 4


            Row {
                Text(
                    modifier = Modifier.width(width),
                    text = "Lose: ${gameViewModel.lose.collectAsState().value}",
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )


                Text(
                    modifier = Modifier.width(width),
                    text = "Win: ${gameViewModel.win.collectAsState().value}",
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )


                Text(
                    text = "Draw: ${gameViewModel.draw.collectAsState().value}",
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}