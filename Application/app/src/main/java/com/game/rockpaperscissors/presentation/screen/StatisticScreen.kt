package com.game.rockpaperscissors.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.composable.BarGraph
import com.game.rockpaperscissors.data.GameModesEnum
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.SelectionType
import com.game.rockpaperscissors.data.WinTyp
import com.game.rockpaperscissors.data.local.database.GameDataEntity
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun StatisticScreen(
    navController: NavController,
    gameData:List<GameDataEntity>
) {

    var displayDate by remember{
        mutableIntStateOf(30)
    }

    val gameDataEntityList = filterGameDataByData(gameData = gameData, days = displayDate)

    var modeList = listOf<dataMode>()


    var yRock = 0
    var yPaper = 0
    var yScissors = 0


    var eRock = 0
    var ePaper = 0
    var eScissors = 0

    gameDataEntityList.forEach{ data ->

        var isAdd = false
        modeList.forEach{modeData->
            if(data.mode == modeData.mode){
                modeData.allPlayedRounds++

                when(data.win){
                    WinTyp.Lose -> modeData.allLose++
                    WinTyp.Win -> modeData.allWins++
                    WinTyp.Draw -> modeData.allDraws++
                }
                isAdd = true
            }
        }

        if(!isAdd){
            val mode = dataMode(
                mode = data.mode,
                allPlayedRounds = 1
            )
            when(data.win){
                WinTyp.Lose -> mode.allLose++
                WinTyp.Win -> mode.allWins++
                WinTyp.Draw -> mode.allDraws++
            }

            modeList = modeList + mode

        }

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
            .background(appColor.background)
    ){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable {
                    navController.popBackStack()
                }
                .background(appColor.secondaryContainer),
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
                    contentDescription = stringResource(id = R.string.go_back),
                    tint = appColor.onBackground
                )
            }

            Text(
                text = stringResource(id = R.string.statistics),
                fontSize = 20.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = appColor.onBackground
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row (
            modifier = Modifier
                .height(28.dp)
                .fillMaxWidth()
                .padding(horizontal = 36.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .height(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.height(28.dp),
                    text = stringResource(id = R.string.last),
                    fontSize = 15.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.45.sp,
                    color = appColor.onBackground,
                    textAlign = TextAlign.Center,


                    )
            }

            Spacer(modifier = Modifier.width(13.dp))


            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(18.dp)
                    .background(if (displayDate != 7) appColor.onSecondaryContainer else appColor.background)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .width(76.dp)
                    .height(28.dp)
                    .background(if (displayDate == 7) appColor.onSecondaryContainer else appColor.background)
                    .clickable {
                        displayDate = 7
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.height(28.dp),
                    text = "7 ${stringResource(id = R.string.days)}",
                    fontSize = 15.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.45.sp,
                    color = appColor.onBackground,
                    textAlign = TextAlign.Center,


                )
            }

            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(18.dp)
                    .background(if (displayDate == 90) appColor.onSecondaryContainer else appColor.background)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .width(76.dp)
                    .height(28.dp)
                    .background(if (displayDate == 30) appColor.onSecondaryContainer else appColor.background)
                    .clickable {
                        displayDate = 30
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.height(28.dp),
                    text = "30 ${stringResource(id = R.string.days)}",
                    fontSize = 15.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.45.sp,
                    color = appColor.onBackground,
                    textAlign = TextAlign.Center

                )
            }

            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(18.dp)
                    .background(if (displayDate == 7) appColor.onSecondaryContainer else appColor.background)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .width(76.dp)
                    .height(28.dp)
                    .background(if (displayDate == 90) appColor.onSecondaryContainer else appColor.background)
                    .clickable {
                        displayDate = 90
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.height(28.dp),
                    text = "90 ${stringResource(id = R.string.days)}",
                    fontSize = 15.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.45.sp,
                    color = appColor.onBackground,
                    textAlign = TextAlign.Center

                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        val barName = listOf(stringResource(id = R.string.rock), stringResource(id = R.string.paper), stringResource(
            id = R.string.scissors
        ))

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
                            .background(appColor.secondaryContainer)
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
                    name = barName,
                    height = 302,
                    title = "${stringResource(id = R.string.your_selection)}:"
                )
            }

            item {
                Spacer(modifier = Modifier.height(53.dp))
            }

            item {
                BarGraph(
                    values = listOf(eRock.toFloat(), ePaper.toFloat(), eScissors.toFloat()),
                    name = barName,
                    height = 302,
                    title = "${stringResource(id = R.string.enemy_selection)}:"
                )
            }


            item {
                Spacer(modifier = Modifier.height(44.dp))

                Text(
                    text = "${stringResource(id = R.string.modes)}:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Oswald,
                    letterSpacing = 0.48.sp,
                    color = appColor.onBackground
                )
            }


            items(modeList){mode->
                Modes(
                    mode = mode.mode,
                    rounds = mode.allPlayedRounds,
                    wins = mode.allWins,
                    loses = mode.allLose,
                    clicked = {
                        navController.navigate("${Screen.ModeStatisticScreen.route}/$it")
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

            }

            item {
                Spacer(modifier = Modifier.height(44.dp))

                Text(
                    text = "${stringResource(id = R.string.all_rounds)}:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Oswald,
                    letterSpacing = 0.48.sp,
                    color = appColor.onBackground
                )
            }

            items(gameDataEntityList){ gameData ->
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
                        .background(appColor.onSecondaryContainer))
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}


@Composable
fun AllRounds(
    rounds: Int,
    yourWins: Int,
    enemyWins: Int,
    win: WinTyp,
    date: String,
    mode: GameModesEnum,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .width(287.dp)
            .height(77.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "$mode",
                fontSize = 13.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Normal,
                color = appColor.onBackground

            )

            Text(
                text = date,
                fontSize = 16.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
                color = appColor.onBackground
            )
        }
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = "${stringResource(id = R.string.rounds)}: ",
                fontSize = 13.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Normal,
                color = appColor.onBackground
            )

            Text(
                text = "$rounds",
                fontSize = 24.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
                color = appColor.onBackground
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 40.dp, top = 12.dp),
            contentAlignment = Alignment.Center
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        modifier = Modifier.width(70.dp),
                        text = stringResource(id = R.string.your),
                        fontSize = 10.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp,
                        textAlign = TextAlign.Right,
                        color = appColor.onBackground
                    )
                    Text(
                        text = "$yourWins",
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        textAlign = TextAlign.Right,
                        color = appColor.onBackground
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "$win",
                    fontSize = 20.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp,
                    color = appColor.onBackground
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        modifier = Modifier.width(70.dp),
                        text = stringResource(id = R.string.enemy),
                        fontSize = 10.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp,
                        textAlign = TextAlign.Left,
                        color = appColor.onBackground
                    )

                    Text(
                        modifier = Modifier.height(27.dp),
                        text = "$enemyWins",
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        textAlign = TextAlign.Left,
                        color = appColor.onBackground
                    )

                    Spacer(modifier = Modifier.height(3.dp))
                }
            }
        }
    }
}



@Composable
fun Modes(
    mode: GameModesEnum,
    rounds: Int,
    wins: Int,
    loses: Int,
    clicked: (GameModesEnum) -> Unit
) {

    Box(
        modifier = Modifier
            .width(288.dp)
            .height(82.dp)
            .clickable {
                clicked(mode)
            }
    ) {
        Row (
            modifier = Modifier.fillMaxSize()
        ){
            Box(
                modifier = Modifier
                    .size(82.dp)
                    .background(appColor.onBackground)
            ){

            }

            Box(modifier = Modifier.fillMaxSize()){
                Box{
                    Text(
                        text = "$mode",
                        fontSize = 20.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Bold,
                        color = appColor.onBackground
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.TopEnd
                ){
                    Box(modifier = Modifier.padding(end = 8.dp)){
                        Text(
                            text = "You Played \nthis mode",
                            fontSize = 10.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 14.sp,
                            color = appColor.onBackground

                        )
                    }


                    Box(modifier = Modifier.padding(top = 19.dp)){
                        Text(
                            text = "$rounds",
                            fontSize = 32.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Right,
                            color = appColor.onBackground

                        )
                    }

                    Box(modifier = Modifier.padding(top = 62.dp)){
                        Text(
                            text = stringResource(id = R.string.times),
                            fontSize = 10.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.Medium,
                            color = appColor.onBackground

                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Box(
                        modifier = Modifier
                            .padding(end = 167.dp, bottom = 14.dp),
                        contentAlignment = Alignment.BottomEnd
                    ){
                        Text(
                            text = "$wins",
                            fontSize = 15.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Right,
                            color = appColor.onBackground

                        )
                        Box(
                            modifier = Modifier.padding(bottom = 18.dp),
                            contentAlignment = Alignment.BottomEnd
                        ){
                            Text(
                                text = "${stringResource(id = R.string.win)}:",
                                fontSize = 10.sp,
                                fontFamily = Oswald,
                                fontWeight = FontWeight.Light,
                                textAlign = TextAlign.Right,
                                color = appColor.onBackground

                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Box(
                        modifier = Modifier
                            .padding(end = 121.dp, bottom = 14.dp),
                        contentAlignment = Alignment.BottomEnd
                    ){
                        Text(
                            text = "$loses",
                            fontSize = 15.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Right,
                            color = appColor.onBackground

                        )
                        Box(
                            modifier = Modifier.padding(bottom = 18.dp),
                            contentAlignment = Alignment.BottomEnd
                        ){
                            Text(
                                text = "${stringResource(id = R.string.lose)}:",
                                fontSize = 10.sp,
                                fontFamily = Oswald,
                                fontWeight = FontWeight.Light,
                                textAlign = TextAlign.Right,
                                color = appColor.onBackground

                            )
                        }
                    }
                }
            }
        }
    }
}



fun filterGameDataByData(gameData: List<GameDataEntity>, days: Int): List<GameDataEntity>{
    val deleteTime = LocalDateTime.now().minusDays(days.toLong())

    var gameDatas = emptyList<GameDataEntity>()

    for (data in gameData){
        if(data.timestamp.isAfter(deleteTime)){
            gameDatas = gameDatas + data
        }
    }

    return gameDatas
}


data class dataMode(
    var mode: GameModesEnum,
    var allPlayedRounds: Int = 0,
    var allWins: Int = 0,
    var allLose: Int = 0,
    var allDraws: Int = 0
)















