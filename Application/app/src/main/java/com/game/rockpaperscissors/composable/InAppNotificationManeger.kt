package com.game.rockpaperscissors.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.viewModel.InAppNotification
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor
import kotlinx.coroutines.delay

@Composable
fun InAppNotificationManager(
    notification: List<InAppNotification>,
    navController: NavController,
    onDelete: (InAppNotification) -> Unit,
    multiple: Boolean = false,
    onClick: (InAppNotification, Int) -> Unit
) {

    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 32.dp, end = 32.dp)
        ) {

            var i = 0

            items(notification) { appNotification ->
                SwipeToDeleteContainer(
                    item = appNotification,
                    onDelete = {
                        onDelete(appNotification)
                    }
                ) {
                    when (appNotification) {
                        is InAppNotification.FriendRequest -> {

                            FriendRequestPushUp(
                                userName = appNotification.userName,
                                onClick = {
                                    navController.navigate(Screen.FriendsScreen.route)
                                    onDelete(appNotification)
                                },
                                onSelect = {
                                    val i = if (it) 1 else 2
                                    onClick(appNotification, i)
                                }
                            )
                        }

                        is InAppNotification.PlayWithFriend -> TODO()
                        null -> {}
                    }
                }

                if (multiple && i + 1 != notification.size) {

                    Spacer(modifier = Modifier.height(12.dp))

                }
                i++
            }


        }
    }
}



@Composable
fun FriendRequestPushUp(userName: String, onClick: () -> Unit, onSelect: (Boolean) -> Unit) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(26.dp))
        .background(appColor.onSecondaryContainer)
        .clickable {
            onClick()
        }
    ){
        Row {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(appColor.onSecondary)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "hallo"
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .height(54.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userName,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    letterSpacing = 1.2.sp,
                    color = appColor.onBackground
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .height(54.dp)
                    .padding(bottom = 13.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = "sent you a friend request",
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    letterSpacing = 0.2.sp,
                    color = appColor.onBackground
                )
            }
        }
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .padding(horizontal = 12.dp)
        ) {
            val with = this@BoxWithConstraints.maxWidth
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ){
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(this@BoxWithConstraints.maxHeight / 2)
                        .background(appColor.secondary)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),){
                Box(
                    modifier = Modifier
                        .width(with / 2)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {

                            onSelect(true)

                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "accept",
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        letterSpacing = 0.2.sp,
                        color = appColor.secondary
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                Box(
                    modifier = Modifier
                        .width(with / 2)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {

                            onSelect(false)

                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "not accept",
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        letterSpacing = 0.2.sp,
                        color = appColor.secondary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart || value == SwipeToDismissBoxValue.StartToEnd) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {},
            dismissContent = { content(item) },
        )
    }
}