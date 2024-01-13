package com.game.rockpaperscissors.composable.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.ArrowForwardIos
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.data.GameModesEnum
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.local.database.PlayerData
import com.game.rockpaperscissors.data.viewModel.GameViewModel
import com.game.rockpaperscissors.data.viewModel.PlayerViewModel
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor

//@Preview
@Composable
fun GameSettingScreen(
    navController: NavController,
    gameViewModel: GameViewModel,
    mode: GameModesEnum,
    playerData: PlayerData,
    enemyData: List<PlayerData>
){
    gameViewModel.gameMode = mode
    gameViewModel.player = playerData

    when(mode){
        GameModesEnum.RANDOM -> {
            val i = (enemyData.indices).random()

            val enemy = enemyData[i]
            gameViewModel.enemy = enemy
        }
        GameModesEnum.LOCAL_MULTIPLAYER -> {
            val i = (enemyData.indices).random()

            val enemy = enemyData[i]
            gameViewModel.enemy = enemy

        }
        GameModesEnum.ONLINE_MULTIPLAYER -> {

        }
        GameModesEnum.AI_MODE -> {

        }
    }

    var selectedRounds by remember {
        mutableIntStateOf(1)
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
                    contentDescription = "Go Back",
                    tint = appColor.onBackground,
                )
            }

            Text(
                text = "Game Settings",
                fontSize = 20.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = appColor.onBackground
            )
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, bottom = 54.dp, end = 24.dp),
            contentAlignment = Alignment.BottomCenter
        ) {

            Column {

                BoxWithConstraints (
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                ){

                    val width = this.maxWidth / 4
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 62.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (selectedRounds == 1) appColor.secondary else appColor.secondaryContainer)
                                .width(width)
                                .clickable {
                                    selectedRounds = 1

                                }
                                .padding(top = 8.dp, bottom = 10.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "3",
                                fontSize = 16.sp,
                                fontFamily = Oswald,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                color = appColor.onBackground
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (selectedRounds == 2) appColor.secondary else appColor.secondaryContainer)
                                .width(width)
                                .clickable {

                                    selectedRounds = 2

                                }
                                .padding(top = 8.dp, bottom = 10.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "5",
                                fontSize = 16.sp,
                                fontFamily = Oswald,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                color = appColor.onBackground
                            )
                        }


                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (selectedRounds == 3) appColor.secondary else appColor.secondaryContainer)
                                .width(width)
                                .clickable {
                                    selectedRounds = 3
                                }
                                .padding(top = 8.dp, bottom = 10.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "13 ",
                                fontSize = 16.sp,
                                fontFamily = Oswald,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                color = appColor.onBackground
                            )
                        }
                    }
                }



                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(appColor.secondaryContainer)
                        .fillMaxWidth()
                        .clickable {
                            gameViewModel.setRounds(
                                when (selectedRounds) {
                                    1 -> 3
                                    2 -> 5
                                    3 -> 13
                                    else -> 3
                                }
                            )
                            navController.navigate(Screen.GameScreen.route)
                        }
                        .padding(top = 10.dp, bottom = 12.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Start",
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = appColor.onBackground
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowForwardIos,
                            contentDescription = "Start",
                            tint = appColor.onBackground
                        )
                    }
                }
            }
        }
    }
}