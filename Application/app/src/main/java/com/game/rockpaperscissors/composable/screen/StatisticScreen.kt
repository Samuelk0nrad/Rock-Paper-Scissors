package com.game.rockpaperscissors.composable.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.composable.BarGraph
import com.game.rockpaperscissors.ui.theme.Oswald

@Preview
@Composable
fun StatisticScreen(
//    navController: NavController
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable {
//                    navController.popBackStack()
                }
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.TopCenter
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 6.dp, start = 12.dp),
                contentAlignment = Alignment.TopStart

            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIos,
                    contentDescription = "Go Back"
                )
            }

            Text(
                text = "Statistics",
                fontSize = 20.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Last",
                fontSize = 15.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.45.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(13.dp))


            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(18.dp)
                    .background(MaterialTheme.colorScheme.onSecondaryContainer)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .width(76.dp)
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "7 days",
                    fontSize = 15.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.45.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(18.dp)
                    .background(MaterialTheme.colorScheme.onSecondaryContainer)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .width(76.dp)
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.onSecondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "30 days",
                    fontSize = 15.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.45.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(18.dp)
                    .background(MaterialTheme.colorScheme.onSecondaryContainer)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .width(76.dp)
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "90 days",
                    fontSize = 15.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.45.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 36.dp)
        ){

            item {
                Spacer(modifier = Modifier.height(15.dp))
            }

            item {
                Column {
                    Text(
                        text = "Your Activity",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Oswald,
                        letterSpacing = 0.48.sp
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    ){

                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "you play on average per day: ",
                        fontFamily = Oswald,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(53.dp))
            }

            item {
                BarGraph(
                    values = listOf(1f, 2f, 3f),
                    name = listOf("Rock", "Paper", "Scissors"),
                    height = 302,
                    title = "Your Selection:"
                )
            }

            item {
                Spacer(modifier = Modifier.height(53.dp))
            }

            item {
                BarGraph(
                    values = listOf(1f, 2f, 3f),
                    name = listOf("Rock", "Paper", "Scissors"),
                    height = 302,
                    title = "Enemy Selection:"
                )
            }

            item {
                Spacer(modifier = Modifier.height(44.dp))

                Text(
                    text = "Modes:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Oswald,
                    letterSpacing = 0.48.sp
                )
            }

            var i = 0

            items(3){
                Modes()

                Spacer(modifier = Modifier.height(15.dp))
                Spacer(modifier = Modifier.height(1.dp).width(316.dp).background(MaterialTheme.colorScheme.secondaryContainer))
                Spacer(modifier = Modifier.height(15.dp))
                i++
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun Modes() {

    Box(
        modifier = Modifier
            .width(287.dp)
            .height(77.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            Text(
                text = "Name of the Mode",
                fontSize = 13.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground

            )

            Text(
                text = "21.10.1233",
                fontSize = 16.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Row (
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.Bottom
        ){
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = "Rounds: ",
                fontSize = 13.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "10",
                fontSize = 24.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 55.dp),
            contentAlignment = Alignment.Center
        ){

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ){
                Column (
                    horizontalAlignment = Alignment.End
                ){
                    Text(
                        modifier = Modifier.width(70.dp),
                        text = "You",
                        fontSize = 10.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp,
                        textAlign = TextAlign.Right
                    )
                    Text(
                        text = "000",
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        textAlign = TextAlign.Right
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Win",
                    fontSize = 20.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp,
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        modifier = Modifier.width(70.dp),
                        text = "Enemy",
                        fontSize = 10.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp,
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = "000",
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        textAlign = TextAlign.Left
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                }
            }


        }
    }

}












