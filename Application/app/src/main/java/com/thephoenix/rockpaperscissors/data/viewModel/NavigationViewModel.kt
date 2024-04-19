package com.thephoenix.rockpaperscissors.data.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thephoenix.rockpaperscissors.data.PlayerDataState
import com.thephoenix.rockpaperscissors.data.local.database.PlayerDataDao
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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

    private val _isLoggedIn = MutableStateFlow(state.value.allPlayer.isNotEmpty())
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _isDatabaseLoaded = MutableStateFlow(false)
    val isDatabaseLoaded = _isDatabaseLoaded.asStateFlow()

    private val _isReady = MutableStateFlow(false)

    val isReady = _isReady.asStateFlow()


    init {
        viewModelScope.launch {
            delay(1000)

            _isReady.value = true
            _isLoggedIn.value = state.value.allPlayer.isNotEmpty()
            Firebase.messaging.subscribeToTopic("weather").await()

        }
    }




}





