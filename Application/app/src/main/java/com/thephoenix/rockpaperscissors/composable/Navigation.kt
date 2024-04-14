package com.thephoenix.rockpaperscissors.composable

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat.startActivity
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
import com.thephoenix.rockpaperscissors.data.Screen
import com.thephoenix.rockpaperscissors.SetBarColor
import com.thephoenix.rockpaperscissors.SignUpInActivity
import com.thephoenix.rockpaperscissors.presentation.screen.edit_profile.EditProfileScreen
import com.thephoenix.rockpaperscissors.presentation.screen.friends.FriendsScreen
import com.thephoenix.rockpaperscissors.presentation.screen.game.profile.GamePlayerProfileScreen
import com.thephoenix.rockpaperscissors.presentation.screen.game.GameViewModel
import com.thephoenix.rockpaperscissors.presentation.screen.game.settings.GameSettingScreen
import com.thephoenix.rockpaperscissors.presentation.screen.game.statistics.GameStatisticScreen
import com.thephoenix.rockpaperscissors.presentation.screen.HomeScreen
import com.thephoenix.rockpaperscissors.presentation.screen.ModeStatisticScreen
import com.thephoenix.rockpaperscissors.presentation.screen.profile.ProfileScreen
import com.thephoenix.rockpaperscissors.presentation.screen.RoundStatisticScreen
import com.thephoenix.rockpaperscissors.presentation.screen.StatisticScreen
import com.thephoenix.rockpaperscissors.data.GameModesEnum
import com.thephoenix.rockpaperscissors.data.local.database.GameDataEntity
import com.thephoenix.rockpaperscissors.data.local.database.GameDataEvent
import com.thephoenix.rockpaperscissors.data.viewModel.GameDataViewModel
import com.thephoenix.rockpaperscissors.data.viewModel.MainViewModel
import com.thephoenix.rockpaperscissors.presentation.screen.OnlineMultiplayerGameNavigation
import com.thephoenix.rockpaperscissors.presentation.screen.edit_profile.EditProfileViewModel
import com.thephoenix.rockpaperscissors.presentation.screen.friends.FriendsViewModel
import com.thephoenix.rockpaperscissors.presentation.screen.game.local_multiplayer.LocalMultiplayerGameScreen
import com.thephoenix.rockpaperscissors.presentation.screen.game.random.RandomGameScreen
import com.thephoenix.rockpaperscissors.presentation.screen.profile.ProfileViewModel
import com.thephoenix.rockpaperscissors.ui.theme.appColor

@Composable
fun Navigation(
    context: Context,
    mainViewModel: MainViewModel
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route ){

        navigation(
            route = Screen.MainGame.route,
            startDestination = Screen.GameSettingScreen.route
        ){


            composable(
                route = "${Screen.GameSettingScreen.route}/{mode}/{round}",
                arguments = listOf(
                        navArgument("mode"){type = NavType.StringType},
                        navArgument("round"){type = NavType.BoolType}
                    )

            ){ entry->
                SetBarColor(appColor.secondaryContainer, appColor.background)

                val gameViewModel = entry.sharedViewModel<GameViewModel>(navController = navController)

                val modeString: String? = entry.arguments?.getString("mode")
                val mode: GameModesEnum = enumValueOf(modeString!!)
                val round: Boolean = entry.arguments?.getBoolean("round") ?: false



                GameSettingScreen(
                    navController = navController,
                    gameViewModel = gameViewModel,
                    mode = mode,
                    ifRounds = round
                )
            }


            composable(route = Screen.RandomGame.route){entry ->
                val viewModel = entry.sharedViewModel<GameViewModel>(navController = navController)
                val gameDataViewModel = hiltViewModel<GameDataViewModel>()

                RandomGameScreen(
                    gameViewModel = viewModel,
                    onEvent = gameDataViewModel::onEvent,
                    navController = navController,
                    context = context
                )
            }

            composable(route = Screen.LocalMultiplayerGameScreen.route){ entry ->
                val viewModel = entry.sharedViewModel<GameViewModel>(navController = navController)
                val gameDataViewModel = hiltViewModel<GameDataViewModel>()

                LocalMultiplayerGameScreen(
                    gameViewModel = viewModel,
                    onEvent = gameDataViewModel::onEvent,
                    navController = navController,
                    context = context
                )
            }


            composable(route = Screen.OnlineMultiplayerGame.route){entry ->

                val viewModel = entry.sharedViewModel<GameViewModel>(navController = navController)
                val gameDataViewModel = hiltViewModel<GameDataViewModel>()


                OnlineMultiplayerGameNavigation(
                    navController = navController,
                    mode = viewModel.gameMode,
                    gameViewModel = viewModel,
                    onEvent = gameDataViewModel::onEvent,
                    context = context
                )
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


                GamePlayerProfileScreen(
                    navController = navController,
                    viewModel = viewModel
                )

            }
        }

        composable(route = Screen.HomeScreen.route){
            SetBarColor(appColor.secondaryContainer, appColor.background)

            val viewModel = hiltViewModel<ProfileViewModel>()

            viewModel.updateUserData()


            HomeScreen(navController, viewModel = viewModel)

        }

        composable(route = Screen.ProfileScreen.route){
            SetBarColor(colorStatus = appColor.secondaryContainer, colorNavigation = appColor.background)

            val viewModel = hiltViewModel<ProfileViewModel>()
            if(viewModel.userData.collectAsState().value == null){
                viewModel.updateUserData()
                Log.d("currentUser ---df---","${viewModel.userData.collectAsState().value?.email}")
            }

            ProfileScreen(
                navController = navController,
                viewModel = viewModel,
                context = context
            )
        }

        composable(route = Screen.EditProfileScreen.route){
            SetBarColor(colorStatus = appColor.secondaryContainer, colorNavigation = appColor.background)
            val viewModel = hiltViewModel<EditProfileViewModel>()

            EditProfileScreen(
                navController = navController,
                context = context,
                viewModel = viewModel
            )

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

            composable(route = Screen.Setting.route){

            }

            composable(route = Screen.FriendsScreen.route){
                val viewModel = hiltViewModel<FriendsViewModel>()

                FriendsScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable(route = Screen.SignInActivity.route){
                val intent = Intent(context, SignUpInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(context, intent, null)
            }
        }

        composable(route = Screen.Setting.route){

            


        }

        composable(route = Screen.FriendsScreen.route){
            val viewModel = hiltViewModel<FriendsViewModel>()
            FriendsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(route = Screen.SignInActivity.route){
            val intent = Intent(context, SignUpInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(context, intent, null)
        }

    }


    val state = mainViewModel.state.collectAsState()


    if(state.value.display){
        InAppNotificationManager(
            notification = state.value.notification,
            navController = navController,
            onDelete = mainViewModel::onDismissNotification,
            multiple = state.value.notification.size > 1,
            onClick = mainViewModel::onClickAction
        )
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