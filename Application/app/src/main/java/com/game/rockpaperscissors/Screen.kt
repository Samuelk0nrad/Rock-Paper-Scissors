package com.game.rockpaperscissors

sealed class Screen(val route: String){

    object WelcomeScreen : Screen("welcome_screen")

    object MainGame : Screen("main_game")
    object GameScreen : Screen("game_screen")
    object GameStatisticScreen : Screen("game_statistic_screen")
}
