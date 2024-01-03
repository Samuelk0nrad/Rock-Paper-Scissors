package com.game.rockpaperscissors.composable.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.composable.BarGraph
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.SelectionType
import com.game.rockpaperscissors.data.WinTyp
import com.game.rockpaperscissors.data.local.database.GameDataEntity
import com.game.rockpaperscissors.ui.theme.Oswald

@Composable
fun RoundStatisticScreen(
    navController: NavController,
    round: GameDataEntity
) {

    var yRock = 0
    var yPaper = 0
    var yScissors = 0


    var eRock = 0
    var ePaper = 0
    var eScissors = 0

    var win = 0
    var lose = 0
    var draw = 0


    round.allRounds.forEach{
        when(it.enemySelection){
            SelectionType.ROCK -> eRock++
            SelectionType.PAPER -> ePaper++
            SelectionType.SCISSORS -> eScissors++
        }


        when(it.playerSelection){
            SelectionType.ROCK -> yRock++
            SelectionType.PAPER -> yPaper++
            SelectionType.SCISSORS -> yScissors++
        }

        when(it.result){
            WinTyp.Lose -> lose++
            WinTyp.Win -> win++
            WinTyp.Draw -> draw++
        }
    }



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
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .clickable {
                               navController.popBackStack()
                    },
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "#${round.id} Statistics",
                    fontSize = 20.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = Color.White
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 6.dp, start = 12.dp),
                    contentAlignment = Alignment.TopStart

                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIos,
                        contentDescription = "Go Back"
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.height(lazyHeight)
            ) {
                val barName = listOf("Rock", "Paper", "Scissors")
                item {
                    WinStatistic(
                        win = win,
                        lose =lose,
                        draw = draw
                    )
                }
                item {
                    BarGraph(
                        values = listOf(yRock.toFloat(), yPaper.toFloat(), yScissors.toFloat()),
                        modifier = Modifier.padding(28.dp),
                        barPadding = 64.dp,
                        height = 362,
                        name = barName,
                        title = "Your Selection"
                    )
                }
                item {
                    BarGraph(
                        values = listOf(eRock.toFloat(), ePaper.toFloat(), eScissors.toFloat()),
                        name = barName,
                        height = 362,
                        barPadding = 64.dp,
                        modifier = Modifier.padding(28.dp),
                        title = "Enemy Selection"
                    )
                }
            }
        }
    }
}