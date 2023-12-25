package com.game.rockpaperscissors.composable.screen

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.ui.theme.Oswald



@Composable
fun ProfileScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(89.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
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
                        contentDescription = "Go Back"
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



                        },
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Go Back",
                        tint = MaterialTheme.colorScheme.onBackground
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
                        .background(MaterialTheme.colorScheme.onBackground),
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
                            .background(MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(22.dp),
                            imageVector = Icons.Rounded.CameraAlt,
                            contentDescription = "Camera",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }


        LazyColumn{

            item {
                Spacer(modifier = Modifier.height(28.dp))

                ProfileNames(name = "Samuel Konrad", title = "Full Name")
            }

            item {
                Spacer(modifier = Modifier.height(36.dp))

                ProfileNames(name = "ThePhoenix", title = "User Name")
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
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start
                        )


                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "2.4.2003",
                            fontSize = 20.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.6.sp,
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.onBackground
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
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start
                        )


                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Mal",
                            fontSize = 20.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.6.sp,
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.onBackground
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
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "All",
                                fontSize = 16.sp,
                                fontFamily = Oswald,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Icon(
                                modifier = Modifier.size(45.dp),
                                imageVector = Icons.Rounded.BarChart,
                                contentDescription = "Profile Picture",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "Statistics",
                                fontSize = 16.sp,
                                fontFamily = Oswald,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
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
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Your",
                                fontSize = 16.sp,
                                fontFamily = Oswald,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Icon(
                                modifier = Modifier.size(45.dp),
                                imageVector = Icons.Rounded.PersonPin,
                                contentDescription = "Profile Picture",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "Friends",
                                fontSize = 16.sp,
                                fontFamily = Oswald,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }

            item {
                Column {
                    Spacer(modifier = Modifier.height(15.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .padding(horizontal = 45.dp)
                            .border(width = 1.dp, color = Color(0xFFFF0000), shape = RoundedCornerShape(size = 57.dp))
                            .clip(RoundedCornerShape(57.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Delete Account",
                            fontSize = 16.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.Light,
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.8.sp,
                            color = Color(0xFFFF0000)
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
            color = MaterialTheme.colorScheme.onBackground
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
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(5.dp))

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .width(178.dp)
                .background(MaterialTheme.colorScheme.onBackground)
        )
    }


}










