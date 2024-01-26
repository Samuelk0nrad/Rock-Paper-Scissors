package com.game.rockpaperscissors.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.presentation.auth.SignInViewModel
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel,
) {


    val email = viewModel.email.collectAsState()
    val password = viewModel.password.collectAsState()

    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .background(appColor.background)
    ){padding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(appColor.background)
                .padding(top = 135.dp, start = 31.dp, end = 31.dp),
        ) {

            Text(
                text = "Login",
                fontSize = 30.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                color = appColor.onBackground
            )


            Spacer(modifier = Modifier.height(80.dp))


            CustomTextFiled(
                email.value,
                onValueChange = { viewModel.updateEmail(it) },
                placeholder = "E-Mail",
                startPadding = 0.dp
            )

            Spacer(modifier = Modifier.height(43.dp))

            CustomTextFiled(
                password.value,
                onValueChange = { viewModel.updatePassword(it) },
                placeholder = "password",
                startPadding = 0.dp,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Forgot password?",
                fontSize = 13.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Right,
                color = appColor.onBackground,
                letterSpacing = 0.65.sp,
                textDecoration = TextDecoration.Underline,
            )





            Spacer(modifier = Modifier.height(72.dp))



            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(appColor.secondaryContainer)
                    .fillMaxWidth()
                    .clickable {

                        viewModel.onSignUpClick(
                            goToScreen = {
                                navController.navigate(Screen.LogedAlreadyIn.route)
                            },
                            errorHandling = {

                            }
                        )

                    }
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Login",
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = appColor.onBackground
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            Row {
                Text(
                    text = "Don't have an account? ",
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
                    text = "Sign Up",
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Left,
                    color = appColor.onBackground,
                    letterSpacing = 0.65.sp,
                    textDecoration = TextDecoration.Underline,
                )
            }

            Spacer(modifier = Modifier.height(34.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(33.dp),
                contentAlignment = Alignment.Center
            ){
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(appColor.onSecondaryContainer)
                )

                Text(
                    modifier = Modifier
                        .background(appColor.background)
                        .padding(horizontal = 12.dp),
                    text = "OR",
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Light,
                    color = appColor.onSecondaryContainer,
                    letterSpacing = 1.28.sp,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            SocialLogin{
                navController.navigate(Screen.LogedAlreadyIn.route)
            }
        }
    }
}





