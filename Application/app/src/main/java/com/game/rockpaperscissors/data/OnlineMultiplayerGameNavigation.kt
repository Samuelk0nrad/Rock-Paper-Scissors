package com.game.rockpaperscissors.data

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.game.rockpaperscissors.data.GameModesEnum.*

@Composable
fun OnlineMultiplayerGameNavigation(
    navController: NavHostController,
    mode: GameModesEnum,
) {
    var startDestination = Screen.OnlineMultiplayerSearchScreen.route

    when(mode){
        ONLINE_MULTIPLAYER -> {
            startDestination = Screen.OnlineMultiplayerSearchScreen.route
        }
        FRIEND_MULTIPLAYER -> {
            startDestination = Screen.FriendsSearchScreen.route
        }
        else -> TODO()
    }


    NavHost(navController = navController, startDestination = startDestination){


        composable(route = Screen.OnlineMultiplayerSearchScreen.route){
            
        }

        composable(route = Screen.FriendsSearchScreen.route){

        }

        composable(
            route = "${Screen.OnlineMultiplayerGameScreen.route}/{gameDestination}",
            arguments = listOf(navArgument("gameDestination"){type = NavType.StringType})
        ){entry ->
            val gameId = entry.arguments?.getString("gameDestination")





        }

    }
}






