package com.thephoenix.rockpaperscissors.presentation.screen.reset_passwort

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.thephoenix.rockpaperscissors.R
import com.thephoenix.rockpaperscissors.data.Screen
import com.thephoenix.rockpaperscissors.presentation.screen.CustomTextFiled
import com.thephoenix.rockpaperscissors.ui.theme.Oswald
import com.thephoenix.rockpaperscissors.ui.theme.appColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel,
    navController: NavController
) {
    val email = viewModel.email.collectAsState()



    var isLoading by remember {
        mutableStateOf(false)
    }

    var isSend by remember {
        mutableStateOf(false)
    }



    var eMailError by remember {
        mutableStateOf(false)
    }
    var eMailEText by remember {
        mutableStateOf("")
    }
    var errorText by remember {
        mutableStateOf("")
    }



    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .background(appColor.background)
    ){padding->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(appColor.background)
                .padding(top = 10.dp, start = 31.dp, end = 31.dp),
        ) {

            item {
                Spacer(modifier = Modifier.height(125.dp))

                Text(
                    text = stringResource(id = R.string.forgot_password),
                    fontSize = 30.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = appColor.onBackground
                )


                Spacer(modifier = Modifier.height(80.dp))

            }


            item {

                Spacer(modifier = Modifier.height(33.dp))

                CustomTextFiled(
                    email.value,
                    onValueChange = {
                        viewModel.updateEmail(it)
                        eMailError = false
                        eMailEText = ""
                        errorText = ""
                    },
                    placeholder = stringResource(id = R.string.e_mail),
                    startPadding = 0.dp,
                    lineColor = if(eMailError) appColor.red else appColor.onBackground
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = eMailEText,
                    fontSize = 12.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.ExtraLight,
                    color = appColor.red
                )
            }

            item {
                Spacer(modifier = Modifier.height(58.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if(isSend) stringResource(id = R.string.successful_sent_the_e_mail) else errorText,
                    fontSize = 18.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    color = if(isSend) appColor.onBackground else appColor.red
                )

                Spacer(modifier = Modifier.height(16.dp))

            }

            item {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(appColor.secondaryContainer)
                        .fillMaxWidth()
                        .clickable {

                            if (!isSend) {

                                viewModel.onSignInClick(
                                    goToScreen = {
                                        isSend = true
                                    },
                                    errorHandling = { error: String? ->
                                        when (error) {
                                            "Given String is empty or null" -> {
                                                if (email.value == "") {
                                                    eMailError = true
                                                    eMailEText = ""
                                                }
                                            }

                                            "The supplied auth credential is incorrect, malformed or has expired." -> {
                                                errorText = "Invalid email or password."
                                            }

                                            "The email address is badly formatted." -> {
                                                eMailError = true
                                                eMailEText = error
                                            }

                                            "The email address or password is incorrect." -> {
                                                errorText = error
                                            }

                                            "There is no user record corresponding to this identifier. The user may have been deleted." -> {
                                                errorText = error
                                            }

                                            "The user account has been disabled by an administrator." -> {
                                                errorText = error
                                            }

                                            "We have blocked all requests from this device due to unusual activity. Try again later." -> {
                                                errorText = error
                                            }

                                            "The provided custom claim attributes are invalid." -> {
                                                errorText = error
                                            }

                                            "A network error (such as timeout, interrupted connection, or unreachable host) has occurred." -> {
                                                errorText =
                                                    "Network error. Check your connection and try again."
                                            }

                                            "An unknown error occurred." -> {
                                                errorText = error
                                            }

                                            null -> {}
                                            else -> {
                                                errorText = error
                                            }
                                        }
                                    },
                                    onValueChange = {

                                        isLoading = it

                                    }
                                )
                            } else {
                                navController.navigate(Screen.LoginScreen.route)
                            }
                        }
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if(isSend) stringResource(id = R.string.go_to_log_in) else stringResource(
                            id = R.string.send_reset_password_e_mail
                        ),
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = appColor.onBackground
                    )
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


}