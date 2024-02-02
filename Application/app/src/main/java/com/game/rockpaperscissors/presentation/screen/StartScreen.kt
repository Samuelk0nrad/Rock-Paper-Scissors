package com.game.rockpaperscissors.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    navController: NavController
){
    Scaffold (
        modifier = Modifier.fillMaxSize()
            .background(appColor.background)
    ){padding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(appColor.background)
                .padding(top = 115.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "RockPaperScissors",
                fontSize = 30.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.5.sp,
                color = appColor.onBackground
            )

            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = "Play the classic game of\n rock, paper, scissors",
                fontSize = 16.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                letterSpacing = 1.28.sp,
                color = appColor.onBackground
            )

            Spacer(modifier = Modifier.height(55.dp))

            //Welcome Image
            Box(
                modifier = Modifier.align(Alignment.End)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.welcome_ilustration),
                    contentDescription = "Welcome Illustration",
                )
            }


            //Start Button -> to "" Activity
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, bottom = 64.dp, end = 24.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(appColor.secondaryContainer)
                        .fillMaxWidth()
                        .clickable {

                            navController.navigate(Screen.WelcomeScreen.route)

                        }
                        .padding(top = 10.dp, bottom = 10.dp)
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
                }
            }
        }
    }
}













