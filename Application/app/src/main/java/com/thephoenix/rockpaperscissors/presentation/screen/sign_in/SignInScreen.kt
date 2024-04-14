package com.thephoenix.rockpaperscissors.presentation.screen.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.thephoenix.rockpaperscissors.R
import com.thephoenix.rockpaperscissors.data.Screen
import com.thephoenix.rockpaperscissors.presentation.screen.CustomTextFiled
import com.thephoenix.rockpaperscissors.ui.theme.Oswald
import com.thephoenix.rockpaperscissors.ui.theme.appColor

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel,
) {


    val email = viewModel.email.collectAsState()
    val password = viewModel.password.collectAsState()



    var isLoading by remember {
        mutableStateOf(false)
    }




    var eMailError by remember {
        mutableStateOf(false)
    }

    var passwordError by remember {
        mutableStateOf(false)
    }


    var eMailEText by remember {
        mutableStateOf("")
    }

    var passwordEText by remember {
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
                    text = stringResource(id = R.string.login),
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
                    placeholder = stringResource(id = R.string.e_mail_or_user_name),
                    startPadding = 0.dp,
                    lineColor = if(eMailError) appColor.red else appColor.onBackground,
                    keyboardOptions =  KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = eMailEText,
                    fontSize = 12.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.ExtraLight,
                    color = appColor.red
                )
                CustomTextFiled(
                    password.value,
                    onValueChange = {
                        viewModel.updatePassword(it)
                        passwordError = false
                        passwordEText = ""
                        errorText = ""

                    },
                    placeholder = stringResource(id = R.string.password),
                    startPadding = 0.dp,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    lineColor = if(passwordError) appColor.red else appColor.onBackground
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = passwordEText,
                    fontSize = 12.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.ExtraLight,
                    color = appColor.red
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.ResetPasswordScreen.route)
                        },
                    text = stringResource(id = R.string.forgot_password) + "?",
                    fontSize = 13.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Right,
                    color = appColor.onBackground,
                    letterSpacing = 0.65.sp,
                    textDecoration = TextDecoration.Underline,
                )
            }

            item {
                Spacer(modifier = Modifier.height(58.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorText,
                    fontSize = 18.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    color = appColor.red
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

                            viewModel.onSignInClick(
                                goToScreen = {
                                    navController.navigate(Screen.LogedAlreadyIn.route)
                                },
                                errorHandling = { error: String? ->
                                    when (error) {
                                        "Given String is empty or null" -> {
                                            if (email.value == "") {
                                                eMailError = true
                                                eMailEText = ""
                                            }
                                            if (password.value == "") {
                                                passwordError = true
                                                passwordEText = ""
                                            }
                                        }

                                        "The supplied auth credential is incorrect, malformed or has expired." -> {
                                            errorText = "Invalid email or username or password."
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
                        }
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.login),
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = appColor.onBackground
                    )
                }

            }

            item {
                Spacer(modifier = Modifier.height(35.dp))

                Row {
                    Text(
                        text = stringResource(id = R.string.dont_have_an_account),
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left,
                        color = appColor.onBackground,
                        letterSpacing = 0.65.sp,
                    )
                    Text(
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.SignUpScreen.route)
                        },
                        text = stringResource(id = R.string.sign_up),
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left,
                        color = appColor.onBackground,
                        letterSpacing = 0.65.sp,
                        textDecoration = TextDecoration.Underline,
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





