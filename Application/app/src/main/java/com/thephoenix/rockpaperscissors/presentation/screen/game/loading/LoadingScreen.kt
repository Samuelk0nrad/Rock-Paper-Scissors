package com.thephoenix.rockpaperscissors.presentation.screen.game.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thephoenix.rockpaperscissors.R
import com.thephoenix.rockpaperscissors.ui.theme.Oswald
import com.thephoenix.rockpaperscissors.ui.theme.appColor

@Composable
fun LoadingScreen(
    exitLogic: () -> Unit,
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor.background)
            .padding(horizontal = 31.dp),
    ){
        Text(
            modifier = Modifier.padding(top = 160.dp),
            text = text,
            fontFamily = Oswald,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = appColor.onBackground
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(color = appColor.onBackground)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 150.dp),
            contentAlignment = Alignment.BottomCenter
        ){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(appColor.secondaryContainer)
                    .fillMaxWidth()
                    .clickable {
                        exitLogic()
                    }
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.exit),
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