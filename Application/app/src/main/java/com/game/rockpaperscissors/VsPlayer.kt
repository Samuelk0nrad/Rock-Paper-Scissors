package com.game.rockpaperscissors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game.rockpaperscissors.ui.theme.Oswald


@Preview
@Composable
fun VsPlayer(
    currentRound: Int = 12,
    rounds: Int = 12,
    enemyWins: Int = 1,
    playerWins: Int = 1
){
    var eWins by remember {
        mutableIntStateOf(enemyWins)
    }

    var pWins by remember {
        mutableIntStateOf(playerWins)
    }

    pWins = playerWins
    eWins = enemyWins
    

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(115.dp)
            .background(MaterialTheme.colorScheme.background)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                modifier = Modifier
                    .padding(bottom = 0.dp),
                painter = painterResource(id = R.drawable.ic_vs),
                contentDescription = "VS.",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        Box{
            Row {
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(start = 5.dp, end = 5.dp, top = 5.dp),
                    text = "$currentRound",
                    fontSize = 63.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.BottomStart


                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = 5.dp, bottom = 20.dp),
                        text = "/$rounds",
                        fontSize = 20.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        Box(
            modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){

            Text(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 0.dp),
                text = "VS",
                fontSize = 40.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Normal
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ){
            Column (
                modifier = Modifier
                    .padding(end = 8.dp)
            ){

                Text(
                    text = "$eWins",
                    fontSize = 20.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$pWins",
                    fontSize = 20.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}





