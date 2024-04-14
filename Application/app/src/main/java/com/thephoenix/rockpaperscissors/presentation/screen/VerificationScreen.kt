package com.thephoenix.rockpaperscissors.presentation.screen

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thephoenix.rockpaperscissors.ui.theme.Oswald
import com.thephoenix.rockpaperscissors.ui.theme.appColor


@Composable
fun VerificationScreen(
    reSendEmail: () -> Unit,
    verifyLater: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = appColor.background
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(252.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Please Verify your E-Mail",
                fontSize = 24.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = appColor.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))


            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Don’t get a E-Mail? ",
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    color = appColor.onBackground
                )

                Text(
                    modifier = Modifier.clickable {
                        reSendEmail()
                    },
                    text = "click to send a new one",
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    color = appColor.onBackground,
                    textDecoration = TextDecoration.Underline,
                )
            }

            Spacer(modifier = Modifier.height(252.dp))


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom =  90.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            verifyLater()
                        },
                    text = "Verify the E-Mail Later",
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = appColor.onBackground,
                    textDecoration = TextDecoration.Underline,
                )
            }
        }
    }
}





