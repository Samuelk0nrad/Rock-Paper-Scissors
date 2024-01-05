package com.game.rockpaperscissors.composable

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.SetBarColor
import com.game.rockpaperscissors.composable.screen.CreateProfileScreen
import com.game.rockpaperscissors.composable.screen.EditProfileScreen
import com.game.rockpaperscissors.composable.screen.GamePlayerProfileScreen
import com.game.rockpaperscissors.data.viewModel.GameViewModel
import com.game.rockpaperscissors.composable.screen.GameScreen
import com.game.rockpaperscissors.composable.screen.GameSettingScreen
import com.game.rockpaperscissors.composable.screen.GameStatisticScreen
import com.game.rockpaperscissors.composable.screen.HomeScreen
import com.game.rockpaperscissors.composable.screen.ModeStatisticScreen
import com.game.rockpaperscissors.composable.screen.ProfileScreen
import com.game.rockpaperscissors.composable.screen.RoundStatisticScreen
import com.game.rockpaperscissors.composable.screen.StatisticScreen
import com.game.rockpaperscissors.composable.screen.WelcomeScreen
import com.game.rockpaperscissors.data.GameModesEnum
import com.game.rockpaperscissors.data.local.database.GameDataEntity
import com.game.rockpaperscissors.data.local.database.GameDataEvent
import com.game.rockpaperscissors.data.viewModel.EnemyViewModel
import com.game.rockpaperscissors.data.viewModel.GameDataViewModel
import com.game.rockpaperscissors.data.viewModel.NavigationViewModel
import com.game.rockpaperscissors.data.viewModel.PlayerViewModel
import com.game.rockpaperscissors.ui.theme.appColor

@Composable
fun Navigation() {

    val navViewModel = hiltViewModel<NavigationViewModel>()
    val isLoggedIn = navViewModel.isLoggedIn

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = if(isLoggedIn) Screen.HomeScreen.route else Screen.LogIn.route ){

        navigation(route = Screen.LogIn.route, startDestination = Screen.WelcomeScreen.route) {
            composable(route = Screen.WelcomeScreen.route) {
                SetBarColor(appColor.background)

                val viewModel = hiltViewModel<PlayerViewModel>()
                val state by viewModel.state.collectAsState()
                if(state.allPlayer.isNotEmpty()){
                    navController.popBackStack()
                    navController.navigate(Screen.HomeScreen.route)
                    Log.d("PlayerViewModel", "isNotEmpty: ${state.allPlayer.size}")
                }


                WelcomeScreen(navController)
            }

            composable(route = Screen.CreateNewAccountScreen.route) {
                SetBarColor(colorSystem = appColor.background)

                val viewModel = hiltViewModel<PlayerViewModel>()
                val state by viewModel.state.collectAsState()

                CreateProfileScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    nevController = navController
                )
            }
        }

        navigation(
            route = Screen.MainGame.route,
            startDestination = Screen.GameSettingScreen.route
        ){
            composable(
                route = "${Screen.GameSettingScreen.route}/{mode}",
                arguments = listOf(navArgument("mode"){type = NavType.StringType})

            ){ entry->
                SetBarColor(appColor.secondaryContainer, appColor.background)

                val gameViewModel = entry.sharedViewModel<GameViewModel>(navController = navController)


                val modeString: String? = entry.arguments?.getString("mode")
                val mode: GameModesEnum = enumValueOf(modeString!!)

                val viewModel = hiltViewModel<PlayerViewModel>()
                val state by viewModel.state.collectAsState()

                val enemyViewModel = hiltViewModel<EnemyViewModel>()
                val enemyState by enemyViewModel.state.collectAsState()


                val enemy = enemyState.allPlayer


                if(state.allPlayer.isNotEmpty() && enemy.isNotEmpty()) {
                    val player = state.allPlayer[0]


                    Log.d("PlayerViewModel", "EnemyViewModel: ${enemy.size}" )
                    GameSettingScreen(
                        navController = navController,
                        gameViewModel = gameViewModel,
                        mode = mode,
                        playerData = player,
                        enemyData = enemy
                    )
                }

            }
            composable(route = Screen.GameScreen.route){ entry->
                val viewModel = entry.sharedViewModel<GameViewModel>(navController = navController)

                SetBarColor(appColor.background)

                val dbViewModel = hiltViewModel<PlayerViewModel>()
                val playerState by dbViewModel.state.collectAsState()

                val gameDataViewModel = hiltViewModel<GameDataViewModel>()
                val gameState by gameDataViewModel.state.collectAsState()

                if(playerState.allPlayer.isNotEmpty()){
                    GameScreen(
                        navController = navController,
                        gameViewModel = viewModel,
                        state = gameState,
                        onEvent = gameDataViewModel::onEvent
                    )
                }
            }
            composable(route = Screen.GameStatisticScreen.route){ entry->
                val viewModel = entry.sharedViewModel<GameViewModel>(navController = navController)
                Log.d("GameViewModel", "${viewModel.draw}")

                SetBarColor(appColor.secondaryContainer, appColor.background)
                GameStatisticScreen(navController,viewModel)
            }
            composable(route = Screen.GamePlayerProfileScreen.route){entry->
                SetBarColor(appColor.secondaryContainer, appColor.background)

                val viewModel = entry.sharedViewModel<GameViewModel>(navController = navController)
                val player = viewModel.selectedPlayer

                if (player != null) {
                    GamePlayerProfileScreen(
                        navController = navController,
                        player = player
                    )
                }
            }
        }

        composable(route = Screen.HomeScreen.route){
            SetBarColor(appColor.secondaryContainer, appColor.background)



            HomeScreen(navController)
        }

        composable(route = Screen.ProfileScreen.route){
            SetBarColor(colorStatus = appColor.secondaryContainer, colorNavigation = appColor.background)

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
            SetBarColor(colorStatus = appColor.secondaryContainer, colorNavigation = appColor.background)
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
        
        navigation(route = Screen.MainStatistic.route, startDestination = Screen.StatisticScreen.route){
            composable(route = Screen.StatisticScreen.route){
                SetBarColor(
                    colorStatus = appColor.secondaryContainer,
                    colorNavigation = appColor.background
                )

                val viewModel = hiltViewModel<GameDataViewModel>()
                val state by viewModel.state.collectAsState()
                val gameData = state.allGames

                if(gameData.isNotEmpty()){
                    StatisticScreen(navController = navController, gameData = gameData)
                }
            }
            
            composable(
                route = "${Screen.ModeStatisticScreen.route}/{mode}",
                arguments = listOf(navArgument("mode"){type = NavType.StringType})
            ){backStackEntry->

                var mode: GameModesEnum? = null

                backStackEntry.arguments?.getString("mode")?.let {
                    mode = enumValueOf<GameModesEnum>(it)
                }

                val viewModel = hiltViewModel<GameDataViewModel>()
                val state by viewModel.state.collectAsState()
                val allGameData = state.allGames


                var gameData: List<GameDataEntity> = emptyList()

                if(mode != null) {

                    allGameData.forEach {
                        if (it.mode == mode){
                            gameData = gameData + it
                        }
                    }

                    ModeStatisticScreen(mode = mode!!, navController = navController, gameData = gameData)
                }
            }

            composable(
                route = "${Screen.RoundStatisticScreen.route}/{roundId}",
                arguments = listOf(navArgument("roundId"){type = NavType.LongType})
            ){backStackEntry->
                val id = backStackEntry.arguments?.getLong("roundId")

                val viewModel = hiltViewModel<GameDataViewModel>()
                val state by viewModel.state.collectAsState()

                viewModel.onEvent(GameDataEvent.GetById(id!!))
                val roundData = state.gameById

                if (roundData != null) {
                    RoundStatisticScreen(navController = navController, round = roundData)
                }
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