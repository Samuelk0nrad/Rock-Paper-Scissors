package com.game.rockpaperscissors.presentation.screen.game.loading

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FriendsMultiplayerSearchViewModel @Inject constructor(
    context: Context
): ViewModel(){

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


    private val _lobbyId = MutableStateFlow("")
    val lobbyId = _lobbyId.asStateFlow()


    private val _gameId = MutableStateFlow("")
    val gameId = _gameId.asStateFlow()


    fun startGame(rounds: Int? = null,callback: (String, Int) -> Unit) {
        var count = 0
        searchLobby{ lobby, playerId, isHost, error ->
            if(count == 0){
                Log.d(TAG, "callback of searchLobby ${lobby}, ${playerId}, $isHost, $error")
                if(isHost == true && lobby != null && playerId != null){
                    _lobbyId.value = lobby
                    Log.d(TAG, "Host is true")

                    foundLobby(rounds!!, lobby){
                        callback(it, rounds)
                    }
                }else if(isHost == false && lobby != null && playerId != null){
                    Log.d(TAG, "Host is false")

                    createGame(lobbyId = lobby, playerId = playerId){gameId, rounds ->
                        callback(gameId, rounds)
                    }
                }
            }
            count++
        }
    }

    private fun searchLobby(callback: (String?, String?, Boolean?, String?) -> Unit){
        if(_player?.username == null || _player.username == ""){
            callback(null, null, null, "")
            return
        }


        val lobbyRef = database.child("reserved_lobby")

        val queryHost: Query = lobbyRef.orderByChild("host").equalTo(_player.username)
        val queryPlayer: Query = lobbyRef.orderByChild("player").equalTo(_player.username)

        Log.d(TAG,"Start searching Lobby ")



        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(lobbySnapshot in dataSnapshot.children){
                    val lobbyKey = lobbySnapshot.key
                    val host = lobbySnapshot.child("host").getValue(String::class.java)
                    val player = lobbySnapshot.child("player").getValue(String::class.java)
                    val isHost = host == _player.username
                    callback(lobbyKey,if(isHost) player else host, isHost, null)
                    return
                }
                callback(null, null,null, "lobby not Found")
                return

            }

            override fun onCancelled(error: DatabaseError) {

                Log.w(TAG,"Error by searching Lobby: ${error.message}")
                callback(null,null, null, error.message)
            }
        }

        queryHost.addListenerForSingleValueEvent(valueEventListener)
        queryPlayer.addValueEventListener(valueEventListener)
    }




    private fun foundLobby(rounds: Int, lobbyId: String?, callback: (String) -> Unit){
        if(lobbyId == null){
            return
        }

        val lobbyRef = database.child("reserved_lobby").child(lobbyId)

        lobbyRef.child("rounds").setValue(rounds)
        waitForGame(lobbyId){gameId, error ->

            if(gameId != null && error == null){
                Log.d(TAG,"Remove lobby: $lobbyId")

                this._gameId.update {
                    gameId
                }

                lobbyRef.removeValue()
                callback(gameId)
            }
        }
    }


    private fun createGame(playerId: String, lobbyId: String, callback: (String, Int) -> Unit){
        Log.d(TAG,"Start creating Game")

        val lobbyRef = database.child("reserved_lobby").child(lobbyId)
        val gameRef = database.child("classic_online_games")

        val newGameRef = gameRef.push()
        _gameId.update {
            newGameRef.key ?: ""
        }

        Log.d(TAG,"Start setting gameId to Lobby lobbyId: $lobbyId , gameId: ${_gameId.value}")



        lobbyRef.child("game").setValue(_gameId.value)
            .addOnSuccessListener {
                Log.d(TAG,"Success set game to Lobby")

            }
            .addOnCanceledListener {
                Log.d(TAG,"Canceled set game to Lobby")

            }

        var rounds = 3

        lobbyRef.child("rounds").get()
            .addOnSuccessListener {
                val round = it.getValue(Int::class.java)

                if(round!= null){
                    rounds = round
                }
            }


        val gameData = hashMapOf(
            "host" to  playerId,
            "player0" to _player?.username,
        )

        newGameRef.setValue(gameData)
            .addOnSuccessListener{
                Log.d(TAG,"Success created Game")
                callback(_gameId.value, rounds)
            }
    }

    private fun waitForGame(lobbyId: String, callback: (String?, String?) -> Unit){
        val valueRef = database.child("reserved_lobby").child(lobbyId).child("game")

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val updatedValue = dataSnapshot.getValue(String::class.java)
                if (updatedValue != null) {
                    // Handle the updated value
                    Log.d(TAG,"get Game: $updatedValue")
                    callback(updatedValue, null)
                    return
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.d(TAG,"Error: ${databaseError.message}")
                callback(null, databaseError.message)
                return
            }
        }

        // Add the ValueEventListener to the reference
        valueRef.addValueEventListener(valueEventListener)
    }

}


