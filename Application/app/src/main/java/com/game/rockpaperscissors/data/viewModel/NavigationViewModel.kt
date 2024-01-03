package com.game.rockpaperscissors.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.rockpaperscissors.data.GameDataState
import com.game.rockpaperscissors.data.PlayerDataState
import com.game.rockpaperscissors.data.local.database.GameDataDao
import com.game.rockpaperscissors.data.local.database.PlayerDataDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
@HiltViewModel
class NavigationViewModel @Inject constructor(
    dao: PlayerDataDao
): ViewModel() {
    private val _allPlayer = dao.allPlayerData().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(PlayerDataState())
    val state = combine(_state, _allPlayer) { state, allPlayer ->
        state.copy(
            allPlayer = allPlayer,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PlayerDataState())

    val isLoggedIn = state.value.allPlayer.isNotEmpty()

}





