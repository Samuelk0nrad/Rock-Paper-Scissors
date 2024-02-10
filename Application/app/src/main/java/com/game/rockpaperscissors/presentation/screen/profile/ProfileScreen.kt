package com.game.rockpaperscissors.presentation.screen.profile

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PersonPin
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor
import java.io.File


@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    context: Context
) {

    val userName = viewModel.userData.collectAsState().value?.username
    val email = viewModel.userData.collectAsState().value?.email
    val profilePicture = viewModel.userData.collectAsState().value?.profilePictureUrl


    var isLoading by remember {
        mutableStateOf(false)
    }

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
                        text = stringResource(id = R.string.your_profile),
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
                Image(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Profile"
                )
                if (profilePicture != "") {
                    AsyncImage(
                        model = profilePicture,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(150.dp),
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }


        LazyColumn{

            item {
                Spacer(modifier = Modifier.height(28.dp))

                ProfileNames(
                    name = email,
                    title = stringResource(id = R.string.e_mail),
                    style = LocalTextStyle.current.copy(
                        fontSize = 13.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.SemiBold,
                        color = appColor.onBackground,
                        textAlign = TextAlign.Center,
                        letterSpacing = 1.04.sp,
                    )
                )
            }

            item {
                Spacer(modifier = Modifier.height(36.dp))

                ProfileNames(name = userName, title = stringResource(id = R.string.user_name))
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
                                text = stringResource(id = R.string.all),
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
                                text = stringResource(id = R.string.statistics),
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
                                text = stringResource(id = R.string.your),
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
                                text = stringResource(id = R.string.friends),
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
                    Spacer(modifier = Modifier.height(43.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .padding(horizontal = 45.dp)
                            .clip(RoundedCornerShape(57.dp))
                            .background(appColor.secondaryContainer)
                            .clickable {
                                viewModel.onClickChangePassword(
                                    onValueChange = {
                                        isLoading = it
                                    }
                                )

                                Toast
                                    .makeText(
                                        context,
                                        "Send successful a reset password E-mail",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.change_password),
                            fontSize = 16.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.8.sp,
                            color = appColor.onBackground
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .padding(horizontal = 45.dp)
                            .clip(RoundedCornerShape(57.dp))
                            .background(appColor.secondaryContainer)
                            .clickable {
                                viewModel.onClickSignOut(
                                    goToScreen = {
                                        navController.navigate(Screen.SignInActivity.route)
                                    },
                                    onValueChange = {
                                        isLoading = it
                                    }
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.log_out),
                            fontSize = 16.sp,
                            fontFamily = Oswald,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.8.sp,
                            color = appColor.purRed
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .padding(horizontal = 45.dp)
                            .border(
                                width = 1.dp,
                                appColor.purRed,
                                shape = RoundedCornerShape(size = 57.dp)
                            )
                            .clip(RoundedCornerShape(57.dp))
                            .clickable {
                                viewModel.onClickDeleteAccount(
                                    goToScreen = {
                                        navController.navigate(Screen.SignInActivity.route)
                                    },
                                    onValueChange = {
                                        isLoading = it
                                    }
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete_account),
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

    if(isLoading){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(appColor.background.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
        }
    }
}


@Composable
fun ProfileNames(
    name: String?,
    title: String,
    style: TextStyle = LocalTextStyle.current.copy(
        fontSize = 20.sp,
        fontFamily = Oswald,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        letterSpacing = 1.6.sp,
        color = appColor.onBackground
    )
) {
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
            text = name ?: "",
            style = style
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






