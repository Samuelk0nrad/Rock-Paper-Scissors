package com.game.rockpaperscissors.composable.screen

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.data.PlayerDataState
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.local.database.PlayerData
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor

@Composable
fun GamePlayerProfileScreen(
    navController: NavController,
    player: PlayerData
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor.background)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(89.dp)
                    .background(appColor.secondaryContainer)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 6.dp, start = 12.dp),
                    contentAlignment = Alignment.TopStart

                ) {
                    Icon(
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        },
                        imageVector = Icons.Rounded.ArrowBackIos,
                        contentDescription = "Go Back",
                        tint = appColor.onBackground
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = "Your Profile",
                        fontSize = 20.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 6.dp, end = 12.dp),
                    contentAlignment = Alignment.TopEnd

                ) {
                    Icon(
                        modifier = Modifier.clickable {

                            navController.navigate(Screen.EditProfileScreen.route)

                        },
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Go Back",
                        tint = appColor.onBackground
                    )
                }


            }


            Box(
                modifier = Modifier
                    .padding(top = 46.dp)
                    .height(86.dp)
                    .width(86.dp)

            ) {

                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(100.dp))
                        .background(appColor.onBackground),
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Profile"
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {


                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .clickable {


                            }
                            .background(appColor.background),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(22.dp),
                            imageVector = Icons.Rounded.CameraAlt,
                            contentDescription = "Camera",
                            tint = appColor.onBackground
                        )
                    }
                }
            }
        }


        LazyColumn{

            item {
                Spacer(modifier = Modifier.height(28.dp))

                ProfileNames(name = player.name, title = "Full Name")
            }

            item {
                Spacer(modifier = Modifier.height(36.dp))

                ProfileNames(name = player.userName, title = "User Name")
            }

            item {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 62.dp)
                ){
                    Spacer(modifier = Modifier.height(40.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "birth date",
                            fontSize = 20.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.Light,
                            letterSpacing = 1.6.sp,
                            color = appColor.onBackground,
                            textAlign = TextAlign.Start
                        )


                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = player.birthData,
                            fontSize = 20.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.6.sp,
                            textAlign = TextAlign.End,
                            color = appColor.onBackground
                        )
                    }
                }
            }

            item {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 62.dp)
                ){
                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Gender",
                            fontSize = 20.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.Light,
                            letterSpacing = 1.6.sp,
                            color = appColor.onBackground,
                            textAlign = TextAlign.Start
                        )


                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = player.gender,
                            fontSize = 20.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.6.sp,
                            textAlign = TextAlign.End,
                            color = appColor.onBackground
                        )
                    }
                }
            }
        }
    }
}

















