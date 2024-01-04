package com.game.rockpaperscissors.data.viewModel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.rockpaperscissors.data.PlayerDataState
import com.game.rockpaperscissors.data.local.database.PlayerData
import com.game.rockpaperscissors.data.local.database.PlayerDataDao
import com.game.rockpaperscissors.data.local.database.PlayerDataEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PlayerViewModel @Inject constructor(
    @Named("playerDao")
    private val dao: PlayerDataDao
): ViewModel() {

    private val _allPlayer = dao.allPlayerData().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(PlayerDataState())
    val state = combine(_state, _allPlayer) { state, allPlayer ->
        state.copy(
            allPlayer = allPlayer,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PlayerDataState())

    fun onEvent(event: PlayerDataEvent){
        when(event){
            PlayerDataEvent.CreateNewPlayer -> {
                val fullName = _state.value.fullName
                val userName = _state.value.userName
                val birthData = _state.value.birthData
                val gender = _state.value.gender
                val userImage = _state.value.userImage
                val showData = _state.value.showData
                val showName = _state.value.showName

                if (fullName.isBlank() ||
                    userName.isBlank() ||
                    birthData.isBlank()
                    ){

                    Log.d("Room.databaseBuilder.PlayerDatabase","return")
                    return
                }


                val playerData = PlayerData(
                    name = fullName,
                    userName = userName,
                    birthData = birthData,
                    gender = gender,
                    userImage = userImage,
                    showName = showName,
                    showData = showData,
                )



                state.value.allPlayer.forEach{data ->
                    viewModelScope.launch {
                        dao.deletePlayer(data)
                        Log.d("Room.databaseBuilder.PlayerDatabase","delete")
                    }

                }




                viewModelScope.launch {
                    dao.upsertPlayer(playerData)
                }

                _state.update { it.copy(
                    fullName = "",
                    userName = "",
                    birthData = "",
                    gender = "",
                    userImage = "",
                    showData = true,
                    showName = true
                ) }

            }
            is PlayerDataEvent.SetFullName -> {
                _state.update { it.copy(
                    fullName = event.fullName
                ) }
            }
            is PlayerDataEvent.SetGender -> {
                _state.update { it.copy(
                    gender = event.gender
                ) }
            }
            is PlayerDataEvent.SetShowData -> {
                _state.update { it.copy(
                    showData = event.showData
                ) }
            }
            is PlayerDataEvent.SetShowName -> {
                _state.update { it.copy(
                    showName = event.showName
                ) }
            }
            is PlayerDataEvent.SetUserImage -> {
                _state.update { it.copy(
                    userImage = event.image
                ) }
            }
            is PlayerDataEvent.SetUserName -> {
                _state.update { it.copy(
                    userName = event.userName
                ) }
            }
            is PlayerDataEvent.SetBirthData -> {
                _state.update { it.copy(
                    birthData = event.birthData
                ) }
            }
            is PlayerDataEvent.DeletePlayer -> {
                viewModelScope.launch {
                    dao.deletePlayer(event.playerData)
                }
            }
        }
    }
}