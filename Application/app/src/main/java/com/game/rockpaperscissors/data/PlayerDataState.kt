package com.game.rockpaperscissors.data

import android.widget.ImageView
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.data.local.database.PlayerData
import com.game.rockpaperscissors.data.local.database.PlayerDataEvent

data class PlayerDataState(
    val allPlayer: List<PlayerData> = emptyList(),
    val fullName: String = "",
    val userName: String = "",
    val birthData: String = "",
    val gender: String = "---",
    val userImage: String = "",
    val showData: Boolean = true,
    val showName: Boolean = true
)
