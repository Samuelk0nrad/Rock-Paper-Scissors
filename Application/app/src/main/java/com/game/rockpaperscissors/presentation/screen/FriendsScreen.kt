package com.game.rockpaperscissors.presentation.screen

import androidx.compose.runtime.Composable
import com.google.firebase.crashlytics.FirebaseCrashlytics

@Composable
fun FriendsScreen() {

    FirebaseCrashlytics.getInstance().log("Hallo World")
    throw Exception("Hello Firebase!!!!!!!!!!")
}


