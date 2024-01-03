package com.game.rockpaperscissors.composable.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.composable.BarGraph
import com.game.rockpaperscissors.data.GameModesEnum
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.SelectionType
import com.game.rockpaperscissors.data.WinTyp
import com.game.rockpaperscissors.data.local.database.GameDataEntity
import com.game.rockpaperscissors.ui.theme.Oswald
import java.time.format.DateTimeFormatter

@Composable
fun ModeStatisticScreen(
    mode: GameModesEnum,
    navController: NavController,
    gameData:List<GameDataEntity>
) {


    var yRock = 0
    var yPaper = 0
    var yScissors = 0


    var eRock = 0
    var ePaper = 0
    var eScissors = 0

    gameData.forEach{data ->
        data.allRounds.forEach{
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
        }
    }



    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable {
                    navController.popBackStack()
                }
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.TopCenter
        ) {

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

            Text(
                text = "$mode Statistics",
                fontSize = 20.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Last",
                fontSize = 15.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.45.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(13.dp))


            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(18.dp)
                    .background(MaterialTheme.colorScheme.onSecondaryContainer)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .width(76.dp)
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "7 days",
                    fontSize = 15.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.45.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(18.dp)
                    .background(MaterialTheme.colorScheme.onSecondaryContainer)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .width(76.dp)
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.onSecondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "30 days",
                    fontSize = 15.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.45.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(18.dp)
                    .background(MaterialTheme.colorScheme.onSecondaryContainer)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .width(76.dp)
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "90 days",
                    fontSize = 15.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.45.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 36.dp)
        ){
/*
            item {
                Spacer(modifier = Modifier.height(15.dp))
            }

            item {
                Column {
                    Text(
                        text = "Your Activity",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Oswald,
                        letterSpacing = 0.48.sp
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    ){

                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "you play on average per day: ",
                        fontFamily = Oswald,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
*/
            item {
                Spacer(modifier = Modifier.height(53.dp))
            }

            item {
                BarGraph(
                    values = listOf(yRock.toFloat(), yPaper.toFloat(), yScissors.toFloat()),
                    name = listOf("Rock", "Paper", "Scissors"),
                    height = 302,
                    title = "Your Selection:"
                )
            }

            item {
                Spacer(modifier = Modifier.height(53.dp))
            }

            item {
                BarGraph(
                    values = listOf(eRock.toFloat(), ePaper.toFloat(), eScissors.toFloat()),
                    name = listOf("Rock", "Paper", "Scissors"),
                    height = 302,
                    title = "Enemy Selection:"
                )
            }



            item {
                Spacer(modifier = Modifier.height(44.dp))

                Text(
                    text = "All Rounds:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Oswald,
                    letterSpacing = 0.48.sp
                )
            }

            items(gameData){gameData ->
                Column {

                    var yourWins = 0
                    var enemyWins = 0
                    gameData.allRounds.forEach{round->
                        when(round.result){
                            WinTyp.Lose -> enemyWins++
                            WinTyp.Win -> yourWins++
                            else -> {}
                        }
                    }

                    val formattedDate = gameData.timestamp.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))

                    AllRounds(
                        rounds = gameData.rounds,
                        yourWins = yourWins,
                        enemyWins = enemyWins,
                        win = gameData.win,
                        date = formattedDate,
                        mode = gameData.mode,
                        onClick = {
                            navController.navigate("${Screen.RoundStatisticScreen.route}/${gameData.id}")
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                    Spacer(modifier = Modifier
                        .height(1.dp)
                        .width(316.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer))
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}
