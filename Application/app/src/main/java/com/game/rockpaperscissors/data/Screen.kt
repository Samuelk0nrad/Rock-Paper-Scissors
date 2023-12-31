package com.game.rockpaperscissors.data

sealed class Screen(val route: String){

    object WelcomeScreen : Screen("welcome_screen")
    object CreateNewAccountScreen : Screen("create_new_account_screen")

    object MainGame : Screen("main_game")
    object GameScreen : Screen("game_screen")
    object GameStatisticScreen : Screen("game_statistic_screen")
    object GameSettingScreen : Screen("game_setting_screen")
    object HomeScreen : Screen("home_screen")
    object ProfileScreen : Screen("profile_screen")
    object LogIn : Screen("log_in")
    object EditProfileScreen : Screen("edit_profile_screen")
    object StatisticScreen : Screen("statistic_screen")
    object ModeStatisticScreen : Screen("mode_statistic_screen")
    object RoundStatisticScreen : Screen("round_statistic_screen")
    object MainStatistic: Screen("main_statistic")
    object GamePlayerProfileScreen: Screen("game_player_profile_screen")
}
