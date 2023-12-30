package com.game.rockpaperscissors.composable

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.SetBarColor
import com.game.rockpaperscissors.composable.screen.CreateProfileScreen
import com.game.rockpaperscissors.composable.screen.EditProfileScreen
import com.game.rockpaperscissors.data.viewModel.GameViewModel
import com.game.rockpaperscissors.composable.screen.GameScreen
import com.game.rockpaperscissors.composable.screen.GameSettingScreen
import com.game.rockpaperscissors.composable.screen.GameStatisticScreen
import com.game.rockpaperscissors.composable.screen.HomeScreen
import com.game.rockpaperscissors.composable.screen.ProfileScreen
import com.game.rockpaperscissors.composable.screen.WelcomeScreen
import com.game.rockpaperscissors.data.viewModel.PlayerViewModel
import com.game.rockpaperscissors.data.viewModel.TestViewModel

@Composable
fun Navigation() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LogIn.route){

        navigation(route = Screen.LogIn.route, startDestination = Screen.WelcomeScreen.route) {
            composable(route = Screen.WelcomeScreen.route) {
                SetBarColor(MaterialTheme.colorScheme.background)

                val viewModel = hiltViewModel<PlayerViewModel>()
                val state by viewModel.state.collectAsState()
                if(state.allPlayer.isNotEmpty()){
                    navController.navigate(Screen.HomeScreen.route)
                }


                WelcomeScreen(navController)
            }

            composable(route = Screen.CreateNewAccountScreen.route) {
                SetBarColor(
                    colorStatus = MaterialTheme.colorScheme.secondaryContainer,
                    colorNavigation = MaterialTheme.colorScheme.background
                )

                val viewModel = hiltViewModel<PlayerViewModel>()
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

                val dbViewModel = hiltViewModel<PlayerViewModel>()
                val state by dbViewModel.state.collectAsState()


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

            val viewModel = hiltViewModel<PlayerViewModel>()
            val state by viewModel.state.collectAsState()

            if(state.allPlayer.isNotEmpty()) {
                ProfileScreen(
                    navController = navController,
                    state = state,
                    deleteAcount = {
                        viewModel.onEvent(it)
                        navController.navigate(Screen.LogIn.route)
                    }
                )
            }

        }

        composable(route = Screen.EditProfileScreen.route){
            SetBarColor(colorStatus = MaterialTheme.colorScheme.secondaryContainer, colorNavigation = MaterialTheme.colorScheme.background)
            val viewModel = hiltViewModel<PlayerViewModel>()
            val state by viewModel.state.collectAsState()
            if(state.allPlayer.isNotEmpty()) {
                EditProfileScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    nevController = navController
                )
            }
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