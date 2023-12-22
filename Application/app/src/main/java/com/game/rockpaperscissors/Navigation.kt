package com.game.rockpaperscissors

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.game.rockpaperscissors.data.ViewModel.GameViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.WelcomeScreen.route){
        composable(route = Screen.WelcomeScreen.route){
            WelcomeScreen(navController)
            SetBarColor(MaterialTheme.colorScheme.background)

        }

        navigation(route = Screen.MainGame.route, startDestination = Screen.GameSettingScreen.route){
            composable(route = Screen.GameSettingScreen.route){entry->
                val viewModel = entry.sharedViewModel<GameViewModel>(navController = navController)

                SetBarColor(MaterialTheme.colorScheme.secondaryContainer)

                GameSettingScreen(navController, viewModel)

            }
            composable(route = Screen.GameScreen.route){entry->
                val viewModel = entry.sharedViewModel<GameViewModel>(navController = navController)

                SetBarColor(MaterialTheme.colorScheme.background)


                GameScreen(
                    navController,
                    viewModel
                )
            }
            composable(route = Screen.GameStatisticScreen.route){entry->
                val viewModel = entry.sharedViewModel<GameViewModel>(navController = navController)
                Log.d("GameViewModel", "${viewModel.draw}")

                SetBarColor(MaterialTheme.colorScheme.secondaryContainer)
                GameStatisticScreen(navController,viewModel)
            }
        }

        composable(route = Screen.HomeScreen.route){
            HomeScreen()
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}