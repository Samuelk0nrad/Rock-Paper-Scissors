package com.thephoenix.rockpaperscissors.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thephoenix.rockpaperscissors.data.PlayerDataState
import com.thephoenix.rockpaperscissors.data.local.database.PlayerDataDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class EnemyViewModel @Inject constructor(
    @Named("enemyDao")
    private val dao: PlayerDataDao
): ViewModel() {

    private val _allEnemy = dao.allPlayerData().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(PlayerDataState())
    val state = combine(_state, _allEnemy) { state, allEnemy ->
        state.copy(
            allPlayer = allEnemy,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PlayerDataState())

    val enemys = _allEnemy
}