package com.thephoenix.rockpaperscissors.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thephoenix.rockpaperscissors.data.Screen
import com.thephoenix.rockpaperscissors.data.viewModel.InAppNotification
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

                        is InAppNotification.PlayWithFriend -> {
                            PlayRequestPushUp(userName = appNotification.userName) {
                                navController.navigate("${Screen.GameSettingScreen.route}/FRIEND_MULTIPLAYER/true")
                                onClick(appNotification, 1)
                            }
                        }
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