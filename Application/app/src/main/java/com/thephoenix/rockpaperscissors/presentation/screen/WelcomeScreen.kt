package com.thephoenix.rockpaperscissors.presentation.screen

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.thephoenix.rockpaperscissors.R
import com.thephoenix.rockpaperscissors.data.Screen
import com.thephoenix.rockpaperscissors.ui.theme.Oswald
import com.thephoenix.rockpaperscissors.ui.theme.appColor

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun WelcomeScreen(
    navController: NavController,
    onGoogleSignIn: () -> Unit,
    context: Context
) {

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
                text = stringResource(id = R.string.welcome),
                fontSize = 30.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                color = appColor.onBackground
            )

            Text(
                modifier = Modifier.height(62.dp),
                text = stringResource(id = R.string.please_login_or_sign_up),
                fontSize = 16.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Left,
                letterSpacing = 1.28.sp,
                color = appColor.onBackground
            )
            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.size(66.dp),
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = null,
                    tint = appColor.onBackground
                )

            }

            Spacer(modifier = Modifier.height(72.dp))



            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(appColor.secondaryContainer)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Screen.SignUpScreen.route)
                    }
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.create_new_account),
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = appColor.onBackground
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                modifier = Modifier.height(35.dp),
                text = stringResource(id = R.string.do_you_have_already_a_account),
                fontSize = 16.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Left,
                letterSpacing = 1.28.sp,
                color = appColor.onBackground
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(appColor.secondaryContainer)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Screen.LoginScreen.route)
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
                    text = stringResource(id = R.string.or),
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
                when(it){
                    "Google" -> onGoogleSignIn()
                    "X" -> {
                        Toast.makeText(
                            context,
                            "Coming soon",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    "GitHub" -> {
                        Toast.makeText(
                            context,
                            "Coming soon",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    "Apple" -> {
                        Toast.makeText(
                            context,
                            "Coming soon",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}


@Composable
fun SocialLogin(onClick: (String) -> Unit){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        SocialLog("Google", painterResource(id = R.drawable.googl)) {
            onClick("Google")
        }


        Spacer(modifier = Modifier.width(34.dp))

        SocialLog("X", painterResource(id = R.drawable.twitter)) {

            onClick("X")

        }


        Spacer(modifier = Modifier.width(34.dp))


        SocialLog("GitHub", painterResource(id = R.drawable.github)) {

            onClick("GitHub")

        }


        Spacer(modifier = Modifier.width(34.dp))

        SocialLog("Apple", painterResource(id = R.drawable.apple)) {

            onClick("Apple")

        }
    }
}

@Composable
fun SocialLog(text: String, printer: Painter, click: () -> Unit) {

    Column (
        Modifier
            .clickable {
                click()
            }
            .clip(RoundedCornerShape(14.dp))
    ){
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .size(54.dp)
                .background(appColor.onSecondary),
            contentAlignment = Alignment.Center

        ){
            Icon(
                modifier = Modifier.size(42.dp),
                painter = printer,
                contentDescription = text,
                tint = appColor.background
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            modifier = Modifier.width(54.dp),
            text = text,
            fontSize = 18.sp,
            fontFamily = Oswald,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = appColor.onBackground
        )
    }
    
}