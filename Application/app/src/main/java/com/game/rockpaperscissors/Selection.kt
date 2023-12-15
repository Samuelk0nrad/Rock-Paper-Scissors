package com.game.rockpaperscissors

import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class Selection {

    var isSelected by mutableStateOf(false)
    var currentSelection by mutableIntStateOf(2)

    constructor(setIsSelected: Boolean, setSelection: Int){
        isSelected = setIsSelected
        Selection = setSelection
    }
    constructor(){
        isSelected = false
        Selection = 2
    }

    var Selection: Int
        get() = currentSelection
        set(value) {
            if(value in 1..3){
                currentSelection = value
            }
        }




    @Composable
    fun Weapon(
        isSelectable: Boolean = true,
        hide:Boolean = false,
        isSelected: Boolean = this.isSelected,
        currentSelection: Int = this.currentSelection
    ){

        var selection by remember {
            mutableIntStateOf(currentSelection)
        }

        var isSelect by remember {
            mutableStateOf(isSelected)
        }

        val transition = updateTransition(
            targetState = selection,
            label = "234"
        )

        if(hide){
            selection = 2
            isSelect = false
        } else if(!isSelectable){
            selection = currentSelection
            isSelect = isSelected
        }else{
            selection = this.currentSelection
            isSelect = this.isSelected
        }
        //Size

        val paperSize by transition.animateFloat(
            transitionSpec = { tween(500) },
            label = "paperSize",
            targetValueByState = { selection1 ->
                if (selection1 == 1) 1.5f else 1f
            }
        )

        val rockSize by transition.animateFloat(
            transitionSpec = { tween(500) },
            label = "rockSize",
            targetValueByState = { selection1 ->
                if (selection1 == 2) 1.5f else 1f
            }
        )

        val scissorsSize by transition.animateFloat(
            transitionSpec = { tween(500) },
            label = "scissorsSize",
            targetValueByState = { selection1 ->
                if (selection1 == 3) 1.5f else 1f
            }
        )


        //padding

        val paperPadding by transition.animateInt(
            transitionSpec = { tween(500) },
            label = "paperPadding",
            targetValueByState = { selection1 ->
                if (selection1 == 1) 22 else 0
            }
        )

        val rockPadding by transition.animateInt(
            transitionSpec = { tween(500) },
            label = "rockPadding",
            targetValueByState = { selection1 ->
                if (selection1 == 2) 22 else 0
            }
        )

        val scissorsPadding by transition.animateInt(
            transitionSpec = { tween(500) },
            label = "scissorsPaddingToTop",
            targetValueByState = { selection1 ->
                if (selection1 == 3) 22 else 0
            }
        )


        //Position

        val paperPositionAlignmentStart by transition.animateFloat (
            transitionSpec = { tween(500) },
            label = "paperPositionAlignmentStart",
            targetValueByState = { selection1 ->
                if (selection1 == 1) 1f else if (selection1 == 2) 0f else -1f
            }
        )
        val paperPositionAlignmentEnd by transition.animateFloat (
            transitionSpec = { tween(500) },
            label = "paperPositionAlignmentEnd",
            targetValueByState = { selection1 ->
                if (selection1 == 1) 1f else if (selection1 == 2) 2f else 3f
            }
        )


        val rockPositionAlignmentStart by transition.animateFloat (
            transitionSpec = { tween(500) },
            label = "rockPositionAlignmentStart",
            targetValueByState = { selection1 ->
                if (selection1 == 1) 2f else if (selection1 == 2) 1f else 0f
            }
        )
        val rockPositionAlignmentEnd by transition.animateFloat (
            transitionSpec = { tween(500) },
            label = "rockPositionAlignmentEnd",
            targetValueByState = { selection1 ->
                if (selection1 == 1) 0f else if (selection1 == 2) 1f else 2f
            }
        )

        val scissorsPositionAlignmentStart by transition.animateFloat (
            transitionSpec = { tween(500) },
            label = "scissorsPositionAlignmentStart",
            targetValueByState = { selection1 ->
                if (selection1 == 1) 3f else if (selection1 == 2) 2f else 1f
            }
        )
        val scissorsPositionAlignmentEnd by transition.animateFloat (
            transitionSpec = { tween(500) },
            label = "scissorsPositionAlignmentEnd",
            targetValueByState = { selection1 ->
                if (selection1 == 1) -1f else if (selection1 == 2) 0f else 1f
            }
        )


        //Color

        val paperColor by transition.animateColor (
            transitionSpec = { tween(500) },
            label = "scissorsColor",
            targetValueByState = { selection1 ->
                if (isSelect){
                    if(selection1 == 1) MaterialTheme.colorScheme.onBackground
                    else MaterialTheme.colorScheme.onSecondary
                }else MaterialTheme.colorScheme.secondary
            }
        )

        val rockColor by transition.animateColor (
            transitionSpec = { tween(500) },
            label = "scissorsColor",
            targetValueByState = { selection1 ->
                if (isSelect){
                    if(selection1 == 2) MaterialTheme.colorScheme.onBackground
                    else MaterialTheme.colorScheme.onSecondary
                }else MaterialTheme.colorScheme.secondary
            }
        )

        val scissorsColor by transition.animateColor (
            transitionSpec = { tween(500) },
            label = "scissorsColor",
            targetValueByState = { select ->
                if (isSelect){
                    if(select == 3) MaterialTheme.colorScheme.onBackground
                    else MaterialTheme.colorScheme.onSecondary
                }else MaterialTheme.colorScheme.secondary
            }
        )




        val width: Int = 342
        Box(
            modifier = Modifier
                .width(width.dp)
                .height(163.dp)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.TopCenter
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

                val with: Float = (this.maxWidth / 3).value

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    //.padding(end = 120.dp)
                    contentAlignment = if (selection == 3) Alignment.TopEnd else Alignment.TopStart
                ) {


                    Box(modifier = Modifier
                        .padding(
                            end = (with * paperPositionAlignmentEnd).dp,
                            start = (with * (if(paperPositionAlignmentStart <= 0f) 0f else paperPositionAlignmentStart)).dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width((with).dp)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(top = paperPadding.dp)
                                    .size(width = (63 * paperSize).dp, height = (70 * paperSize).dp)
                                    .clickable(
                                        interactionSource = MutableInteractionSource(),
                                        indication = null
                                    ) {
                                        if(isSelectable) {
                                            if ((selection == 1) && (!isSelect)) {
                                                this@Selection.isSelected = true
                                                isSelect = true
                                            } else if (!isSelect) {
                                                this@Selection.currentSelection = 1
                                                selection = 1
                                            }
                                            Log.d("clickable", "Paper$selection")
                                        }
                                    },
                                painter = painterResource(id = R.drawable.hand_paper),
                                contentDescription = "hand_paper",
                                tint = paperColor
                            )
                        }
                    }

                    Box(modifier = Modifier
                        .padding(
                            end = (with * rockPositionAlignmentEnd).dp,
                            start = (with * rockPositionAlignmentStart).dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width((with).dp)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(top = rockPadding.dp)
                                    .size(width = (63 * rockSize).dp, height = (70 * rockSize).dp)
                                    .clickable(
                                        interactionSource = MutableInteractionSource(),
                                        indication = null
                                    ) {
                                        if(isSelectable) {
                                            if ((selection == 2) && (!isSelect)) {
                                                this@Selection.isSelected = true
                                                isSelect = true
                                            } else if (!isSelect) {
                                                this@Selection.currentSelection = 2
                                                selection = 2
                                            }

                                            Log.d("clickable", "Rock$selection")
                                        }
                                    },
                                painter = painterResource(id = R.drawable.hand_rock),
                                contentDescription = "hand_rock",
                                tint = rockColor
                            )
                        }
                    }

                    Box(modifier = Modifier
                        .padding(
                            end = (with * (if(scissorsPositionAlignmentEnd <= 0) 0f else scissorsPositionAlignmentEnd)).dp,
                            start = (with * scissorsPositionAlignmentStart).dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width((with).dp)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(top = scissorsPadding.dp)
                                    .size(
                                        width = (63 * scissorsSize).dp,
                                        height = (70 * scissorsSize).dp
                                    )
                                    .clickable(
                                        interactionSource = MutableInteractionSource(),
                                        indication = null
                                    ) {
                                        if(isSelectable) {
                                            if ((selection == 3) && (!isSelect)) {
                                                this@Selection.isSelected = true
                                                isSelect = true
                                            } else if (!isSelect) {
                                                this@Selection.currentSelection = 3
                                                selection = 3
                                            }
                                            Log.d("clickable", "Scissors$selection")
                                        }
                                    },
                                painter = painterResource(id = R.drawable.hand_scissors),
                                contentDescription = "hand_scissors",
                                tint = scissorsColor
                            )
                        }
                    }
                }
            }
        }

        if(!hide){
            this.currentSelection = selection
            this.isSelected = isSelect
        }
    }
}