package com.thephoenix.rockpaperscissors.ui.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Green = Color(0xFF86D87F)
val Red = Color(0xFFD87F7F)

val themeMod = AppTheme.SYSTEM

val currentNightMode = AppCompatDelegate.getDefaultNightMode()




val DarkAppColors = AppColors(
    background = Color(0xFF161616),
    secondaryContainer = Color(0xFF262626),
    onBackground = Color(0xFFD9D9D9),
    green = Color(0xFF86D87F),
    red = Color(0xFFD87F7F),
    purRed = Color(0xFFFF0000),
    onSecondaryContainer = Color(0xFF343434),
    secondary = Color(0xFF5D5D5D),
    onSecondary = Color(0xFF9C9C9C),
)

val LightAppColors = AppColors(
    background = Color(0xFFD9D9D9),
    secondaryContainer = Color(0xFF4F5B43),
    onBackground = Color(0xFF161616),
    green = Color(0xFF86D87F),
    red = Color(0xFFD87F7F),
    purRed = Color(0xFFFF0000),
    onSecondaryContainer = Color(0xFF3F483D),
    secondary = Color(0xFFA5C09E),
    onSecondary = Color(0xFFBCBCDB),
)

val appColor: AppColors = when (themeMod) {
    AppTheme.DARK -> DarkAppColors
    AppTheme.LIGHT -> LightAppColors
    else -> when(currentNightMode){
        AppCompatDelegate.MODE_NIGHT_NO -> LightAppColors
        else -> DarkAppColors
    }
}


data class AppColors(
    val background: Color = Color(0xFF161616),
    val secondaryContainer: Color = Color(0xFF262626),
    val onBackground: Color = Color(0xFFD9D9D9),
    val green: Color = Color(0xFF86D87F),
    val red: Color = Color(0xFFD87F7F),
    val purRed: Color = Color(0xFFD87F7F),
    val onSecondaryContainer: Color = Color(0xFF343434),
    val secondary: Color = Color(0xFF5D5D5D),
    val onSecondary: Color = Color(0xFF9C9C9C),
)


enum class AppTheme{
    DARK,
    LIGHT,
    SYSTEM
}