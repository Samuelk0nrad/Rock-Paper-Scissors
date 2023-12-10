package com.game.rockpaperscissors

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import font.Oswald

@Preview
@Composable
fun Player(
    profilePicture: Painter = painterResource(id = R.drawable.blank_profile_picture),
    userName: String = "User Name",
    isBot: Boolean = true,
    level: Int = 41,
    isReady: Boolean = false

){
    Row(
        modifier = Modifier
            .width(220.dp)
            .height(98.dp)
            .background(MaterialTheme.colorScheme.background)

    ){
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(98.dp)
        ){
            Image(
                painter = profilePicture,
                contentDescription = "Profile Picture"
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column (
            modifier = Modifier.fillMaxHeight()
        ){

            Box() {
                Text(
                    text = userName,
                    fontSize = 18.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground
                )

                var botText = if (isBot == true) "Bot" else ""

                Text(
                    modifier = Modifier.padding(top = 21.dp),
                    text = botText,
                    fontSize = 8.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    modifier = Modifier.padding(top = 30.dp),
                    text = "Level:  $level",
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                if (isReady) {
                    Text(
                        text = "Ready",
                        fontSize = 20.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.36.sp,
                        color = Color.Green
                    )
                } else {
                    Text(
                        text = "Not Ready",
                        fontSize = 20.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.36.sp,
                        color = Color.Red
                    )
                }
            }
        }
    }
}
