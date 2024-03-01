package com.game.rockpaperscissors.presentation.screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.data.GameModesEnum
import com.game.rockpaperscissors.data.GameModesEnum.*
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.local.database.GameDataEvent
import com.game.rockpaperscissors.presentation.screen.game.GameViewModel
import com.game.rockpaperscissors.presentation.screen.game.loading.FriendsMultiplayerSearchViewModel
import com.game.rockpaperscissors.presentation.screen.game.loading.LoadingScreen
import com.game.rockpaperscissors.presentation.screen.game.loading.OnlineMultiplayerSearchViewModel
import com.game.rockpaperscissors.presentation.screen.game.online_multiplayer.OnlineMultiplayerGameScreen
import com.game.rockpaperscissors.presentation.screen.game.online_multiplayer.OnlineMultiplayerGameViewModel

@Composable
fun OnlineMultiplayerGameNavigation(
    navController: NavHostController,
    mode: GameModesEnum,
    gameViewModel: GameViewModel,
    onEvent: (GameDataEvent) -> Unit,
    context: Context
) {
    val startDestination = when(mode){
        ONLINE_MULTIPLAYER -> {
            Screen.OnlineMultiplayerSearchScreen.route
        }
        FRIEND_MULTIPLAYER -> {
            Screen.FriendsSearchScreen.route
        }
        else -> TODO()
    }


    val localNavController = rememberNavController()


    NavHost(navController = localNavController, startDestination = startDestination){


        composable(route = Screen.OnlineMultiplayerSearchScreen.route){
            val viewModel = hiltViewModel<OnlineMultiplayerSearchViewModel>()
            val rounds = gameViewModel.rounds.collectAsState()


            var isGameStarted by remember {
                mutableStateOf(false)
            }

            if(!isGameStarted){
                viewModel.startGame(rounds.value){gameId ->
                    localNavController.navigate("${Screen.OnlineMultiplayerGameScreen.route}/$gameId")
                }
                isGameStarted = true
            }


            LoadingScreen(
                exitLogic = {
                    viewModel.exitPlayerSearch()

                    navController.navigate(Screen.HomeScreen.route)
                },
                text = stringResource(id = R.string.searching_for_a_player)
            )
        }

        composable(route = Screen.FriendsSearchScreen.route){
            val viewModel: FriendsMultiplayerSearchViewModel = hiltViewModel()
            val rounds = gameViewModel.rounds.collectAsState()



            var isGameStarted by remember {
                mutableStateOf(false)
            }


            if(!isGameStarted){
                Log.d("OnlineMultiplayerGameViewModel", "Starting start Game")
                viewModel.startGame{gameId, rounds ->
                    localNavController.navigate("${Screen.OnlineMultiplayerGameScreen.route}/$gameId")
                    gameViewModel.setRounds(rounds)
                }
                isGameStarted = true
            }




            LoadingScreen(
                exitLogic = {


                    navController.navigate(Screen.HomeScreen.route)
                },
                text = stringResource(id = R.string.searching_for_a_player)
            )

        }

        composable(
            route = "${Screen.OnlineMultiplayerGameScreen.route}/{gameId}",
            arguments = listOf(navArgument("gameId"){type = NavType.StringType})
        ){entry ->
            val gameId = entry.arguments?.getString("gameId")
            val viewModel = hiltViewModel<OnlineMultiplayerGameViewModel>()


            if(gameId != null){
                OnlineMultiplayerGameScreen(
                    viewModel = viewModel,
                    gameViewModel = gameViewModel,
                    gameId = gameId,
                    onEvent = onEvent,
                    navController = navController,
                    context = context
                )
            }
        }
    }
}






