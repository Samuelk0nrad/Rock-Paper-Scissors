package com.game.rockpaperscissors.data.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel(){

    //Win/Lose/Draw Count Start//
    private val _lose = MutableStateFlow(0)
    private val _win = MutableStateFlow(0)
    private val _draw = MutableStateFlow(0)
    val lose =_lose.asStateFlow()
    val win =_win.asStateFlow()
    val draw =_draw.asStateFlow()
    fun updateLose(){
        _lose.value++
    }
    fun updateWin(){
        _win.value++
    }
    fun updateDraw(){
        _draw.value++
        Log.d("GameViewModel","$draw")
    }
    //Win/Lose/Draw Count End//


    //Win or Lose Start//
    private val _isWin = MutableStateFlow(false)
    val isWin = _isWin.asStateFlow()

    fun cWin(){
        _isWin.value = _win.value > _lose.value
        Log.d("GameViewModel","${_isWin.value} = ${_win.value} > ${_lose.value}")
    }

    //Win or Lose End//


    //Player/Enemy Selections Start//
    private val _playerSelection = mutableListOf(0f,0f,0f)
    private val _enemySelection = mutableListOf(0f,0f,0f)
    val playerSelection: List<Float>
        get() = _playerSelection
    val enemySelection: List<Float>
        get() = _enemySelection

    fun updatePlayerSelectionRock(){
        _playerSelection[0]++
    }
    fun updatePlayerSelectionPaper(){
        _playerSelection[1]++
    }
    fun updatePlayerSelectionScissors(){
        _playerSelection[2]++
    }
    fun updateEnemySelectionRock(){
        _enemySelection[0]++
    }
    fun updateEnemySelectionPaper(){
        _enemySelection[1]++
    }
    fun updateEnemySelectionScissors(){
        _enemySelection[2]++
    }
    //Player/Enemy Selections End//










}










