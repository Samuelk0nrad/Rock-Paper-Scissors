package com.game.rockpaperscissors.presentation.screen.game

import android.util.Log
import androidx.lifecycle.ViewModel
import com.game.rockpaperscissors.data.GameModesEnum
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel(){

    private val _player: MutableStateFlow<UserData?> = MutableStateFlow(
        Firebase.auth.currentUser?.let {
            UserData(
                userId = it.uid,
                username = it.displayName,
                email = it.email,
                profilePictureUrl = it.photoUrl?.toString()
            )
        }
    )
    val player = _player.asStateFlow()

    private val _enemy: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val enemy = _enemy.asStateFlow()

    fun updateEnemy(newEnemy: UserData){
        _enemy.update {
            newEnemy
        }
    }


    fun updatePlayer(newPlayer: UserData){
        _player.update {
            newPlayer
        }
    }

    var selectedPlayer: UserData? = null

    lateinit var gameMode: GameModesEnum

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
    private val _rounds = MutableStateFlow(3)
    val rounds = _rounds.asStateFlow()

    fun setRounds(round: Int ){
        _rounds.value = round
    }
    //GameSettings  Start//
}










