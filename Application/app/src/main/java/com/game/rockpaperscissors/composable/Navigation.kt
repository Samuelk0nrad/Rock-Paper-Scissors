package com.game.rockpaperscissors.composable

import android.content.Context
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.room.RoomDatabase
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.SetBarColor
import com.game.rockpaperscissors.composable.screen.CreateProfileScreen
import com.game.rockpaperscissors.data.ViewModel.GameViewModel
import com.game.rockpaperscissors.composable.screen.GameScreen
import com.game.rockpaperscissors.composable.screen.GameSettingScreen
import com.game.rockpaperscissors.composable.screen.GameStatisticScreen
import com.game.rockpaperscissors.composable.screen.HomeScreen
import com.game.rockpaperscissors.composable.screen.ProfileScreen
import com.game.rockpaperscissors.composable.screen.WelcomeScreen
import com.game.rockpaperscissors.data.ViewModel.PlayerViewModel
import com.game.rockpaperscissors.data.local.database.PlayerDatabase

@Composable
fun Navigation(context: Context) {

    val playerDB by lazy {
        Room.databaseBuilder(
            context,
            PlayerDatabase::class.java,
            "player.db"
        ).build()
    }


    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.WelcomeScreen.route){

        navigation(route = "", startDestination = Screen.WelcomeScreen.route) {
            composable(route = Screen.WelcomeScreen.route) {
                WelcomeScreen(navController)
                SetBarColor(MaterialTheme.colorScheme.background)

            }

            composable(route = Screen.CreateNewAccountScreen.route) {entry ->
                SetBarColor(
                    colorStatus = MaterialTheme.colorScheme.secondaryContainer,
                    colorNavigation = MaterialTheme.colorScheme.background
                )
                val viewModel = entry.sharedViewModel(navController = navController){
                    PlayerViewModel(playerDB.dao)
                }

                val state by viewModel.state.collectAsState()

                CreateProfileScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    nevController = navController
                )
            }
        }

        navigation(route = Screen.MainGame.route, startDestination = Screen.GameSettingScreen.route){
            composable(route = Screen.GameSettingScreen.route){ entry->
                val viewModel = entry.sharedViewModel<GameViewModel>(navController = navController)

                SetBarColor(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.background)

                GameSettingScreen(navController, viewModel)

            }
            composable(route = Screen.GameScreen.route){ entry->
                val viewModel = entry.sharedViewModel<GameViewModel>(navController = navController)

                SetBarColor(MaterialTheme.colorScheme.background)


                GameScreen(
                    navController,
                    viewModel
                )
            }
            composable(route = Screen.GameStatisticScreen.route){ entry->
                val viewModel = entry.sharedViewModel<GameViewModel>(navController = navController)
                Log.d("GameViewModel", "${viewModel.draw}")

                SetBarColor(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.background)
                GameStatisticScreen(navController,viewModel)
            }
        }

        composable(route = Screen.HomeScreen.route){
            SetBarColor(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.background)

            HomeScreen(navController)
        }

        composable(route = Screen.ProfileScreen.route){
            SetBarColor(colorStatus = MaterialTheme.colorScheme.secondaryContainer, colorNavigation = MaterialTheme.colorScheme.background)

            ProfileScreen(navController)
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

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
    crossinline viewModelFactory: (NavBackStackEntry) -> T
): T {
    // Retrieve the route of the navigation graph containing the current destination
    val navGraphRoute = destination.parent?.route ?: return viewModelFactory(this)

    // Get the NavBackStackEntry of the parent destination (navigation graph)
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    // Use the provided viewModelFactory to create the shared ViewModel with parameters
    return viewModelFactory(parentEntry)
}