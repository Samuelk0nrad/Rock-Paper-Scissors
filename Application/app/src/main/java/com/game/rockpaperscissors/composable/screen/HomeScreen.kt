package com.game.rockpaperscissors.composable.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PersonPin
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.data.GameModes
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.gameModes
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor


@Composable
fun HomeScreen (navController: NavController){

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(appColor.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(appColor.secondaryContainer),
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


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 35.dp, top = 0.dp),

                contentAlignment = Alignment.TopEnd

            ) {
                Box(modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .clickable {
                        navController.navigate(Screen.ProfileScreen.route)
                    }

                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "Profile"
                    )
                }
            }
        }


        LazyColumn{
            item {
                Text(
                    modifier = Modifier.padding(start = 38.dp, bottom = 6.dp, top = 36.dp),
                    text = "Your Last Plays: ",
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp,
                    color = appColor.onBackground
                )
            }


            items(3){count ->
                for (gameMode in gameModes) {
                    if(count +1 == gameMode.number && gameMode.available){
                        DisplayMods(mode = gameMode, navController)
                    }
                }

                Log.d("GameMode", "$count")
            }

            item{

                
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .padding(start = 30.dp, end = 30.dp)
                            .background(appColor.onSecondaryContainer)

                    )
                    
                    Spacer(modifier = Modifier.height(23.dp))
                    
                    
                    val size = 100
                    Row(
                        modifier = Modifier
                            .padding(start = 38.dp, end = 38.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .width(size.dp)
                                .height(size.dp)
                                .clickable {

                                    navController.navigate(Screen.MainStatistic.route)

                                }
                                .background(appColor.secondaryContainer),
                        ) {
                            //First Your Profile
                            Column(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .width(size.dp)
                                    .height(size.dp)
                                    .clickable {

                                        navController.navigate(Screen.ProfileScreen.route)

                                    }
                                    .background(appColor.secondaryContainer),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Your",
                                    fontSize = 16.sp,
                                    fontFamily = Oswald,
                                    fontWeight = FontWeight.Light,
                                    color = appColor.onBackground
                                )
                                Icon(
                                    modifier = Modifier.size(45.dp),
                                    imageVector = Icons.Rounded.Person,
                                    contentDescription = "Profile Picture",
                                    tint = appColor.onBackground
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 2.dp),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Text(
                                    text = "Profile",
                                    fontSize = 16.sp,
                                    fontFamily = Oswald,
                                    fontWeight = FontWeight.Bold,
                                    color = appColor.onBackground
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .width(size.dp)
                                .height(size.dp)
                                .clickable {

                                    navController.navigate(Screen.MainStatistic.route)

                                }
                                .background(appColor.secondaryContainer),
                        ) {
                            //Second All Statistics
                            Column(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .width(size.dp)
                                    .height(size.dp)
                                    .background(appColor.secondaryContainer),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "All",
                                    fontSize = 16.sp,
                                    fontFamily = Oswald,
                                    fontWeight = FontWeight.Light,
                                    color = appColor.onBackground
                                )
                                Icon(
                                    modifier = Modifier.size(45.dp),
                                    imageVector = Icons.Rounded.BarChart,
                                    contentDescription = "Profile Picture",
                                    tint = appColor.onBackground
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 2.dp),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Text(
                                    text = "Statistics",
                                    fontSize = 16.sp,
                                    fontFamily = Oswald,
                                    fontWeight = FontWeight.Bold,
                                    color = appColor.onBackground
                                )
                            }
                        }


                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .width(size.dp)
                                .height(size.dp)
                                .clickable {

                                    navController.navigate(Screen.MainStatistic.route)

                                }
                                .background(appColor.secondaryContainer),
                        ) {
                            // Your Friends
                            Column(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .width(size.dp)
                                    .height(size.dp)
                                    .clickable {


                                    }
                                    .background(appColor.secondaryContainer),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Your",
                                    fontSize = 16.sp,
                                    fontFamily = Oswald,
                                    fontWeight = FontWeight.Light,
                                    color = appColor.onBackground
                                )
                                Icon(
                                    modifier = Modifier.size(45.dp),
                                    imageVector = Icons.Rounded.PersonPin,
                                    contentDescription = "Profile Picture",
                                    tint = appColor.onBackground
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 2.dp),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Text(
                                    text = "Friends",
                                    fontSize = 16.sp,
                                    fontFamily = Oswald,
                                    fontWeight = FontWeight.Bold,
                                    color = appColor.onBackground
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(23.dp))


                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .padding(start = 30.dp, end = 30.dp)
                            .background(appColor.onSecondaryContainer)

                    )

                    Spacer(modifier = Modifier.height(22.dp))

                }
            }
            
            item { 
                Text(
                    modifier = Modifier.padding(start = 38.dp, bottom = 6.dp),
                    text = "Game Modes:",
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp,
                    color = appColor.onBackground
                )
            }

            gameModes.forEach {mode->
                item {
                    DisplayMods(mode = mode, navController)
                }
            }
        }
    }
}




@Composable
fun DisplayMods(mode: GameModes, navController: NavController) {
    Box(modifier = Modifier
        .background(appColor.background)
        .fillMaxWidth()
        .height(122.dp)
        .padding(top = 16.dp, bottom = 16.dp, start = 38.dp, end = 38.dp)
    ){
        Row (
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    if (mode.available) {
                        mode.setToFirst(gameModes)
                        navController.navigate(mode.rout)
                        mode.clickAction
                    }
                }
        ){
            Box(
                modifier = Modifier
                    .height(90.dp)
                    .width(90.dp),
                contentAlignment = Alignment.Center
            ){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = mode.symbol),
                    contentDescription = "Welcome Illustration",
                )
            }

            Spacer(modifier = Modifier.width(22.dp))

            Column {
                Text(
                    text = mode.name,
                    fontSize = 20.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = appColor.onBackground
                )
                Text(
                    text = mode.description,
                    fontSize = 14.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.7.sp,
                    color = appColor.onBackground
                )
            }
        }
        if(!mode.available) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor.background.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.fillMaxSize(0.4f),
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = "Lock",
                    tint = appColor.onBackground
                )
            }
        }
    }
}





