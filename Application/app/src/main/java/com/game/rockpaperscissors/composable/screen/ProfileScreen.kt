package com.game.rockpaperscissors.composable.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PersonPin
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.game.rockpaperscissors.data.PlayerDataState
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.local.database.PlayerData
import com.game.rockpaperscissors.data.local.database.PlayerDataEvent
import com.game.rockpaperscissors.data.viewModel.PlayerViewModel
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor
import java.io.File


@Composable
fun ProfileScreen(navController: NavController, state: PlayerDataState, deleteAcount:(PlayerDataEvent) -> Unit, context: Context) {

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
                    .clip(RoundedCornerShape(100.dp))

            ) {
                val fileName = state.allPlayer[0].userImage

                val imageFile: File? = getImage(context, fileName)


                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(appColor.onBackground),
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Profile"
                )
                if (imageFile != null) {
                    AsyncImage(
                        model = imageFile.toUri(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }


        LazyColumn{

            item {
                Spacer(modifier = Modifier.height(28.dp))

                ProfileNames(name = state.allPlayer[0].name, title = "Full Name")
            }

            item {
                Spacer(modifier = Modifier.height(36.dp))

                ProfileNames(name = state.allPlayer[0].userName, title = "User Name")
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
                            text = state.allPlayer[0].birthData,
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
                            text = state.allPlayer[0].gender,
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

            item{


                Column {

                    Spacer(modifier = Modifier.height(35.dp))


                    val size = 100
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        //Second
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
                            Text(
                                text = "Statistics",
                                fontSize = 16.sp,
                                fontFamily = Oswald,
                                fontWeight = FontWeight.Bold,
                                color = appColor.onBackground
                            )
                        }

                        Spacer(modifier = Modifier.width(36.dp))


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
            }

            item {
                Column {
                    Spacer(modifier = Modifier.height(40.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .padding(horizontal = 45.dp)
                            .border(width = 1.dp, appColor.purRed, shape = RoundedCornerShape(size = 57.dp))
                            .clip(RoundedCornerShape(57.dp))
                            .clickable {
                                state.allPlayer.forEach { playerData ->
                                    deleteAcount(PlayerDataEvent.DeletePlayer(playerData))
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Delete Account",
                            fontSize = 16.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.Light,
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.8.sp,
                            color = appColor.purRed
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ProfileNames(name: String, title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 62.dp, end = 62.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            fontSize = 20.sp,
            fontFamily = Oswald,
            fontWeight = FontWeight.Light,
            letterSpacing = 1.6.sp,
            color = appColor.onBackground
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            modifier = Modifier.width(178.dp),
            text = name,
            fontSize = 20.sp,
            fontFamily = Oswald,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = 1.6.sp,
            color = appColor.onBackground
        )
        Spacer(modifier = Modifier.height(5.dp))

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .width(178.dp)
                .background(appColor.onBackground)
        )
    }


}



fun hideName(input: String): String {
    val words = input.split(" ")
    val result = StringBuilder()

    for (word in words) {
        if (word.isNotEmpty()) {
            result.append(word[0]).append("...")
        }
    }

    // Remove the trailing dots
    result.deleteCharAt(result.length - 1)
    result.deleteCharAt(result.length - 1)

    return result.toString()
}

fun hideDate(input: String): String {
    val reversedInput = input.reversed()
    val dotIndex = reversedInput.indexOf('.')

    return if (dotIndex != -1) {
        reversedInput.substring(0, dotIndex + 1).reversed()
    } else {
        input
    }
}






