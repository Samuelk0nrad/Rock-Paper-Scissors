package com.thephoenix.rockpaperscissors.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.thephoenix.rockpaperscissors.R
import com.thephoenix.rockpaperscissors.ui.theme.Oswald
import com.thephoenix.rockpaperscissors.ui.theme.appColor


@Composable
fun Player(
    profilePicture: String,
    userName: String = "User Name",
    isBot: Boolean = true,
    level: Int = 41,
    isReady: Boolean = false,
    onClick: () -> Unit = {},
){
    Row(
        modifier = Modifier
            .width(220.dp)
            .height(120.dp)
            .clickable {
                onClick()
            }
    ){
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(98.dp)
        ){

            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = ""
            )
            
            AsyncImage(
                model = profilePicture,
                contentDescription = "Profile picture",
                placeholder = painterResource(id = R.drawable.profile),
                modifier = Modifier
                    .size(150.dp),
                contentScale = ContentScale.Crop
            )

        }

        Spacer(modifier = Modifier.width(16.dp))

        Column (
            modifier = Modifier.fillMaxHeight()
        ){

            Box {
                Text(
                    text = userName,
                    fontSize = 18.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    color = appColor.onBackground,
                    maxLines = 1
                )

                val botText = if (isBot) "Bot" else ""

                Text(
                    modifier = Modifier.padding(top = 21.dp),
                    text = botText,
                    fontSize = 8.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    color = appColor.onBackground
                )

                Text(
                    modifier = Modifier.padding(top = 30.dp),
                    text = "${stringResource(id = R.string.level)}:  $level",
                    fontSize = 16.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    color = appColor.onBackground
                )
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                if (isReady) {
                    Text(
                        text = stringResource(id = R.string.ready),
                        fontSize = 20.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.36.sp,
                        color = appColor.green
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.not_ready),
                        fontSize = 20.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.36.sp,
                        color = appColor.red
                    )
                }
            }
        }
    }
}
