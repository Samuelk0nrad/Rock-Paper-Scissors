package com.game.rockpaperscissors

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun Weapon(){

    var selection by remember {
        mutableStateOf(2)
    }



    val transition = updateTransition(
        targetState = selection,
        label = ""
    )

    //Size

    val paperSize by transition.animateFloat(
        transitionSpec = { tween(500) },
        label = "Icon Paper Size",
        targetValueByState = { selection ->
            if (selection == 1) 1.5f else 1f
        }
    )

    val rockSize by transition.animateFloat(
        transitionSpec = { tween(500) },
        label = "Icon Rock Size",
        targetValueByState = { selection ->
            if (selection == 2) 1.5f else 1f
        }
    )

    val scissorsSize by transition.animateFloat(
        transitionSpec = { tween(500) },
        label = "Icon Rock Size",
        targetValueByState = { selection ->
            if (selection == 3) 1.5f else 1f
        }
    )


    //padding

    val paperPadding by transition.animateInt(
        transitionSpec = { tween(500) },
        label = "Icon Paper Size",
        targetValueByState = { selection ->
            if (selection == 1) 22 else 0
        }
    )

    val rockPadding by transition.animateInt(
        transitionSpec = { tween(500) },
        label = "Icon Rock Size",
        targetValueByState = { selection ->
            if (selection == 2) 22 else 0
        }
    )

    val scissorsPadding by transition.animateInt(
        transitionSpec = { tween(500) },
        label = "scissorsPaddingToTop",
        targetValueByState = { selection ->
            if (selection == 3) 22 else 0
        }
    )


    //Position




    val width: Int = 342
    Box(
        modifier = Modifier
            .width(width.dp)
            .height(163.dp)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = TopCenter
    ){

        Row (
            modifier = Modifier
                .width((width / 2.5).dp)
                .height(80.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.onBackground)
            )

            Spacer(
                modifier = Modifier
                    .width((width / 2.5 - 2).dp)
            )

            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.onBackground)
            )
        }

        BoxWithConstraints (
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
        ){
            val with = this.maxWidth / 3

            // For Animation of the Weapons(Movement)
            val leftSpacer by transition.animateDp (
                transitionSpec = { tween(500) },
                label = "leftSpacer",
                targetValueByState = { selection ->
                    if (selection == 1) with else 0.dp
                }
            )

            val reightSpacer by transition.animateDp (
                transitionSpec = { tween(500) },
                label = "leftSpacer",
                targetValueByState = { selection ->
                    if (selection == 3) with else 0.dp
                }
            )

            Box(
                modifier = Modifier
                    //.padding(end = 120.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .width(with)
                            .fillMaxHeight(),
                        contentAlignment = TopCenter
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(top = paperPadding.dp)
                                .size(width = (63 * paperSize).dp, height = (70 * paperSize).dp)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    Log.d("clickable", "Paper")
                                    selection = 1
                                },
                            painter = painterResource(id = R.drawable.hand_paper),
                            contentDescription = "hand_paper",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }


                    Box(
                        modifier = Modifier
                            .width(with)
                            .fillMaxHeight(),
                        contentAlignment = TopCenter
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(top = rockPadding.dp)
                                .size(width = (63 * rockSize).dp, height = (70 * rockSize).dp)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    Log.d("clickable", "Rock")
                                    selection = 2
                                },
                            painter = painterResource(id = R.drawable.hand_rock),
                            contentDescription = "hand_rock",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }


                    Box(
                        modifier = Modifier
                            .width(with)
                            .fillMaxHeight(),
                        contentAlignment = TopCenter
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(top = scissorsPadding.dp)
                                .size(width = (63 * scissorsSize).dp, height = (70 * scissorsSize).dp)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    Log.d("clickable", "Scissors")
                                    selection = 3
                                },
                            painter = painterResource(id = R.drawable.hand_scissors),
                            contentDescription = "hand_scissors",
                            tint = MaterialTheme.colorScheme.secondary,
                        )
                    }
                }
            }
        }
    }
}