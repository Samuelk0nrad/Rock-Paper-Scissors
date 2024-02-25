package com.game.rockpaperscissors.presentation.screen.game.online_multiplayer

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.data.SelectionType
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
@HiltViewModel
class OnlineMultiplayerGameViewModel @Inject constructor (
    context: Context
) : ViewModel() {

    private val TAG = "OnlineMultiplayerGameViewModel"

    private var database: DatabaseReference = Firebase.database(context.getString(R.string.firebase_realtime_database)).reference

    private val _player = Firebase.auth.currentUser?.let {
        UserData(
            userId = it.uid,
            username = it.displayName,
            email = it.email,
            profilePictureUrl = it.photoUrl?.toString()
        )
    }

    private val _currentRound = MutableStateFlow(0)

    private var _enemy: MutableStateFlow<UserData?> = MutableStateFlow(null)

    private val _enemyState: MutableStateFlow<UserState> = MutableStateFlow(
        UserState(
            isReady = false,
            selection = null
        )
    )

    val enemyState = _enemyState.asStateFlow()

    val enemy = _enemy.asStateFlow()

    private val _gameId = MutableStateFlow("")

    fun startGame(gameId: String) {
        _gameId.update {
            gameId
        }
        getEnemyData(gameId)
        getEnemyState()
    }

    fun endGame() {
        val gameRef = database.child("classic_online_games").child(_gameId.value)

        gameRef.get()
            .addOnSuccessListener {
                if(it.exists()){
                    gameRef.removeValue()
                        .addOnSuccessListener {
                            Log.d(TAG,"Successful removed Game")
                        }
                        .addOnCanceledListener {
                            Log.d(TAG,"don't removed Game")

                        }
                }else{
                    Log.d(TAG,"already removed Game")

                }
            }
    }


    private fun getEnemyData(gameId: String){

        getEnemyName(gameId){ playerName ->

            if(playerName != null){
                val userRef = database.child("users").child(playerName)

                userRef.get()
                    .addOnSuccessListener {
                        _enemy.update {enemy->
                            UserData(
                                userId = it.child("userId").getValue(String::class.java) ?:"",
                                username = playerName,
                                email = null,
                                profilePictureUrl = it.child("profile_picture").getValue(String::class.java)
                            )
                        }
                        Log.d(TAG, "Successful get Enemy Data")
                    }
            }
        }
    }

    private fun getEnemyName(gameId: String, callback: (String?) -> Unit){
        if(_player?.username == null || _player.username == ""){
            callback(null)
            return
        }

        val gameRef = database.child("classic_online_games").child(gameId)


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val hostName = dataSnapshot.child("host").getValue(String::class.java)
                if(hostName == _player.username){
                    val playerName = dataSnapshot.child("player0").getValue(String::class.java)

                    callback(playerName)
                }else{
                    callback(hostName)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d(TAG,"get Enemy State went wrong: ${databaseError.message}")

                callback(null)
            }
        }


        gameRef.addListenerForSingleValueEvent(postListener)


    }



    fun updatePlayerState(isReady: Boolean, selection: SelectionType?){

        Log.d(TAG,"Start updating Player State")
        val gameRef = database.child("classic_online_games").child(_gameId.value).child("round${_currentRound.value}")


        gameRef.child("${_player?.username}_is_ready").setValue(isReady)
            .addOnSuccessListener {
                Log.d(TAG,"Successful updated Player is Ready")
            }

        gameRef.child("${_player?.username}_selection").setValue(selection.toString())
            .addOnSuccessListener {
                Log.d(TAG,"Successful updated Player selection")
            }
    }

    fun getEnemyState(){
        val gameRef = database.child("classic_online_games").child(_gameId.value).child("round${_currentRound.value}")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val isReady = dataSnapshot.child("${_enemy.value?.username}_is_ready").getValue(Boolean::class.java)
                val selection = dataSnapshot.child("${_enemy.value?.username}_selection").getValue(String::class.java)

                var selectionError: Boolean

                val enumSelection = try {
                    Log.d(TAG, "selection: $selection")
                    selectionError = false
                    enumValueOf(selection ?:"")
                } catch (e: Exception){
                    selectionError = true
                    Log.d(TAG, "selection: $selection error: ${e.message}")
                    SelectionType.ROCK
                }

                _enemyState.update {
                    it.copy(
                        isReady = if(selectionError) false else isReady ?: false,
                        selection = enumSelection
                    )
                }

                if(isReady == true){
                    return
                }


                Log.d(TAG,"Successful get Enemy State")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d(TAG,"get Enemy State went wrong: ${databaseError.message}")
            }
        }
        gameRef.addValueEventListener(postListener)
    }

    fun updateRound(){
        _currentRound.update {
            it + 1
        }
    }
}

data class UserState(
    val isReady: Boolean,
    val selection: SelectionType?
)


