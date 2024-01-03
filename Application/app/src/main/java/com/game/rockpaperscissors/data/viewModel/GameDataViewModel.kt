package com.game.rockpaperscissors.data.viewModel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.rockpaperscissors.data.GameDataState
import com.game.rockpaperscissors.data.GameModesEnum
import com.game.rockpaperscissors.data.local.database.GameDataDao
import com.game.rockpaperscissors.data.local.database.GameDataEntity
import com.game.rockpaperscissors.data.local.database.GameDataEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameDataViewModel @Inject constructor(
    private val dao: GameDataDao
) : ViewModel(){
    private val _allGames = dao.getGameDataOrderedByDate().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(GameDataState())
    val state = combine(_state,_allGames){state, allGames->
        state.copy(
            allGames =  allGames
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GameDataState())


    fun onEvent(event: GameDataEvent){
        when(event){
            GameDataEvent.CreateNewPlayer -> {
                val mode = _state.value.mode
                val rounds = _state.value.rounds
                val win = _state.value.win
                val allRounds = _state.value.allRounds
                val enemy = _state.value.enemy

                if(rounds == 0 ||
                    win == null ||
                    allRounds.isEmpty()){
                    Log.d("Room.databaseBuilder.PlayerDatabase","return GameDataEvent")
                    return
                }

                val gameData = GameDataEntity(
                    mode = mode,
                    rounds = rounds,
                    win = win,
                    allRounds = allRounds,
                    enemy = enemy
                )

                viewModelScope.launch {
                    dao.upsertGameData(gameData)
                }

                _state.update { it.copy(
                    mode = GameModesEnum.RANDOM,
                    rounds = 0,
                    win = null,
                    allRounds = emptyList(),
                    enemy = null
                )
                }


            }
            is GameDataEvent.SetEnemy -> {
                _state.update { it.copy(
                    enemy = event.enemy
                ) }
            }
            is GameDataEvent.SetMode ->{

                _state.update { it.copy(
                    mode = event.mode
                ) }
            }
            is GameDataEvent.SetNewRound -> {
                val allRounds = _state.value.allRounds + event.oneRound
                Log.d("Room.databaseBuilder.PlayerDatabase","SetNewRound")


                _state.update { it.copy(
                    allRounds = allRounds
                ) }
            }
            is GameDataEvent.SetRounds -> {
                Log.d("Room.databaseBuilder.PlayerDatabase","SetRounds")

                _state.update { it.copy(
                    rounds = event.rounds
                ) }
            }
            is GameDataEvent.SetWin -> {
                Log.d("Room.databaseBuilder.PlayerDatabase","SetWin")

                _state.update { it.copy(
                    win = event.win
                ) }
            }
            is GameDataEvent.GetById -> {
                var gameData: GameDataEntity?
                viewModelScope.launch {
                    gameData = dao.getGameDataById(event.id).firstOrNull()
                    _state.update { it.copy(

                        gameById = gameData
                    ) }
                }
            }
        }
    }
}