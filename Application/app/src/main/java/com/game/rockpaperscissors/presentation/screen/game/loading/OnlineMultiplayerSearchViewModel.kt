package com.game.rockpaperscissors.presentation.screen.game.loading

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OnlineMultiplayerSearchViewModel @Inject constructor(
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


    private val _lobbyId = MutableStateFlow("")
    val lobbyId = _lobbyId.asStateFlow()


    private val _gameId = MutableStateFlow("")
    val gameId = _gameId.asStateFlow()

    fun startGame(rounds: Int,callback: (String) -> Unit) {
        searchLobby(rounds){ lobby,_, error ->
            if(lobby != null){
                _lobbyId.value = lobby
                foundLobby(lobby){
                    callback(it)
                }
            }else if (error != null){
                createLobby(rounds){
                    callback(it)
                }
            }
        }
    }


    fun exitPlayerSearch(){
        if(gameId.value != ""){
            endGame()
        }
        val lobbyRef = database.child("lobby").child(lobbyId.value)

        lobbyRef.get()
            .addOnSuccessListener {
                if(it.exists()){
                    lobbyRef.removeValue()
                }
            }
    }


    private fun endGame() {
        val gameRef = database.child("classic_online_games").child(gameId.value)

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


    private fun searchLobby(rounds: Int, callback: (String?, String?, String?) -> Unit){
        val lobbyRef = database.child("lobby")

        val query: Query = lobbyRef.orderByChild("rounds").equalTo(rounds.toDouble())
        Log.d(TAG,"Start searching Lobby ")


        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var i = 0
                for(lobbySnapshot in dataSnapshot.children){
                    val lobbyKey = lobbySnapshot.key
                    val alreadyFoundPlayer = lobbySnapshot.child("foundPlayer").exists()
                    val createdTime = lobbySnapshot.child("creationTime").getValue(Long::class.java) ?:0
                    val currentTime = System.currentTimeMillis()
                    if(currentTime - createdTime > 1000 * 60 * 60 * 2){
                        if (lobbyKey != null) {
                            lobbyRef.child(lobbyKey).removeValue()
                        }
                    }else if(!alreadyFoundPlayer){

                        val playerId = lobbySnapshot.child("host").getValue(String::class.java)

                        if(playerId == _player?.username){
                            if (lobbyKey != null) {
                                lobbyRef.child(lobbyKey).removeValue()
                            }
                        }else{
                            Log.d(TAG,"lobby Found: $lobbyKey")
                            callback(lobbyKey,playerId, null)
                            return
                        }
                    }

                    i++
                }
                Log.d(TAG,"no Lobby Found: $i")
                callback(null,null, "lobby not Found")

                return
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG,"Error by searching Lobby: ${error.message}")
                callback(null,null, error.message)
            }
        })
    }


    private fun createLobby(rounds: Int, callback: (String) -> Unit){
        val lobbyRef = database.child("lobby")

        val newLobbyRef = lobbyRef.push()


        val lobbyData = hashMapOf(
            "host" to _player?.username,
            "rounds" to rounds,
            "creationTime" to ServerValue.TIMESTAMP,
            "foundPlayer" to null
        )

        newLobbyRef.setValue(lobbyData)
            .addOnSuccessListener{_->
                Log.d(TAG,"Success created Lobby")
            }


        val lobbyId = newLobbyRef.key
        this._lobbyId.update {
            lobbyId ?:""
        }


        waitForPlayer(lobbyId ?:""){player, error->
            if(player == null){
                Log.d(TAG,"callback with player Id is null and Error: $error")

            }else if(error == null){

                createGame(
                    player,
                    lobbyId ?: "",
                ){gameId, _ ->
                    if(gameId != null) callback(gameId)
                }
            }
        }
    }
    private fun waitForPlayer(lobbyId: String, callback: (String?, String?) -> Unit){
        val valueRef = database.child("lobby").child(lobbyId).child("foundPlayer")


        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val updatedValue = dataSnapshot.getValue(String::class.java)
                if (updatedValue != null) {
                    // Handle the updated value
                    callback(updatedValue, null)
                    Log.d(TAG,"found Player: $updatedValue")
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


        Log.d(TAG,"Start searching Player")

        // Add the ValueEventListener to the reference
        valueRef.addValueEventListener(valueEventListener)
    }
    private fun waitForGame(lobbyId: String, callback: (String?, String?) -> Unit){
        val valueRef = database.child("lobby").child(lobbyId).child("game")

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


    private fun foundLobby(lobbyId: String?, callback: (String) -> Unit){

        val lobbyRef = database.child("lobby").child(lobbyId?:"")

        lobbyRef.child("foundPlayer").setValue(_player?.username)
        Log.d(TAG,"set Player Id")

        waitForGame(lobbyId ?:""){gameId, error ->

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
    private fun createGame(playerId: String, lobbyId: String, callback: (String?, String?) -> Unit){
        Log.d(TAG,"Start creating Game")

        val lobbyRef = database.child("lobby").child(lobbyId)
        val gameRef = database.child("classic_online_games")

        val newGameRef = gameRef.push()
        _gameId.update {
            newGameRef.key ?: ""
        }

        Log.d(TAG,"Start setting gameId to Lobby lobbyId: $lobbyId , gameId: ${gameId.value}")



        lobbyRef.child("game").setValue(gameId.value)
            .addOnSuccessListener {
                Log.d(TAG,"Success set game to Lobby")

            }
            .addOnCanceledListener {
                Log.d(TAG,"Canceled set game to Lobby")

            }


        val gameData = hashMapOf(
            "host" to _player?.username,
            "player0" to playerId,
        )

        newGameRef.setValue(gameData)
            .addOnSuccessListener{_->
                Log.d(TAG,"Success created Game")
                callback(gameId.value, null)
            }
    }


}


