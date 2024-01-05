package com.game.rockpaperscissors.data.viewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.rockpaperscissors.data.GameDataState
import com.game.rockpaperscissors.data.PlayerDataState
import com.game.rockpaperscissors.data.local.database.GameDataDao
import com.game.rockpaperscissors.data.local.database.PlayerDataDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NavigationViewModel @Inject constructor(
    @Named("playerDao")
    val dao: PlayerDataDao
): ViewModel() {
    private val _allPlayer = dao.allPlayerData().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(PlayerDataState())
    val state = combine(_state, _allPlayer) { state, allPlayer ->
        state.copy(
            allPlayer = allPlayer,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PlayerDataState())

    val isLoggedIn = state.value.allPlayer.isEmpty()

    private val _isDatabaseLoaded = MutableStateFlow(false)
    val isDatabaseLoaded = _isDatabaseLoaded.asStateFlow()

    private val _isReady = MutableStateFlow(false)

    val isReady = _isReady.asStateFlow()


    init {
        viewModelScope.launch {
            checkDatabase()
            _isReady.value = isDatabaseLoaded.value
        }
    }

    private suspend fun checkDatabase() {
        val count = dao.getCount()
        _isDatabaseLoaded.value = count > 0
    }


}





