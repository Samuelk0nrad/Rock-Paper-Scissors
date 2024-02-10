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

    private val gameId = MutableStateFlow("")
    private val lobbyId = MutableStateFlow("")

    fun startGame(rounds: Int,callback: (Boolean) -> Unit) {
        callback(false)

        searchLobby(rounds){ lobby,player, error ->
            if(lobby != null){
                lobbyId.value = lobby
                foundLobby(lobby, player ?:""){
                    callback(it)
                }
            }else if (error != null){
                createLobby(rounds){
                    callback(it)
                }
            }
        }
    }


    fun endGame() {
        val gameRef = database.child(Childs.OnlineGames.name.value).child(gameId.value)

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

    fun exitPlayerSearch(){
        if(gameId.value != ""){
            endGame()
        }
        val lobbyRef = database.child(Childs.Lobby.name.value).child(lobbyId.value)

        lobbyRef.get()
            .addOnSuccessListener {
                if(it.exists()){
                    lobbyRef.removeValue()
                }
            }
    }


    private fun searchLobby(rounds: Int, callback: (String?, String?, String?) -> Unit){
        val lobbyRef = database.child(Childs.Lobby.name.value)

        val query: Query = lobbyRef.orderByChild("rounds").equalTo(rounds.toDouble())
        Log.d(TAG,"Start searching Lobby ")


        query.addListenerForSingleValueEvent(object : ValueEventListener{
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

    private fun createLobby( rounds: Int, callback: (Boolean) -> Unit){
        val lobbyRef = database.child(Childs.Lobby.name.value)

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
        this.lobbyId.value = lobbyId ?:""
        waitForPlayer(lobbyId ?:""){player, error->
            if(player == null){
                Log.d(TAG,"callback with player Id is null and Error: $error")

            }else if(error == null){

                getEnemyData(player)

                createGame(
                    player,
                    lobbyId ?: "",
                ){gameId, error ->
                    callback(true)
                }
            }
        }
    }

    private fun waitForPlayer(lobbyId: String, callback: (String?, String?) -> Unit){
        val valueRef = database.child(Childs.Lobby.name.value).child(lobbyId).child("foundPlayer")


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
        val valueRef = database.child(Childs.Lobby.name.value).child(lobbyId).child("game")

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

    private fun foundLobby(lobbyId: String?, playerId: String, callback: (Boolean) -> Unit){

        val lobbyRef = database.child(Childs.Lobby.name.value).child(lobbyId?:"")

        lobbyRef.child("foundPlayer").setValue(_player?.username)
        Log.d(TAG,"set Player Id")

        getEnemyData(playerId)

        waitForGame(lobbyId ?:""){gameId, error ->

            if(gameId != null || error == null){
                Log.d(TAG,"Remove lobby: $lobbyId")

                this.gameId.update {
                    gameId ?:""
                }

                lobbyRef.removeValue()
                callback(true)
                getEnemyState()
            }
        }
    }



    private fun createGame(playerId: String, lobbyId: String, callback: (String?, String?) -> Unit){
        Log.d(TAG,"Start creating Game")

        val lobbyRef = database.child(Childs.Lobby.name.value).child(lobbyId)
        val gameRef = database.child(Childs.OnlineGames.name.value)

        val newGameRef = gameRef.push()
        gameId.update {
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
                getEnemyData(_enemy.value?.username ?: "")
                getEnemyState()
            }
    }

    private fun getEnemyData(playerName: String){
        val userRef = database.child("users").child(playerName)

        userRef.get()
            .addOnSuccessListener {
                _enemy.update {enemy->
                    UserData(
                        userId = it.child("userId").getValue(String::class.java) ?:"",
                        username = playerName,
                        email = null,
                        profilePictureUrl = it.child("profilePicture").getValue(String::class.java)
                    )
                }
                Log.d(TAG, "Successful get Enemy Data")
            }


    }



    fun updatePlayerState(isReady: Boolean, selection: SelectionType?){

        Log.d(TAG,"Start updating Player State")
        val gameRef = database.child(Childs.OnlineGames.name.value).child(gameId.value).child("round${_currentRound.value}")


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
        val gameRef = database.child(Childs.OnlineGames.name.value).child(gameId.value).child("round${_currentRound.value}")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val isReady = dataSnapshot.child("${_enemy.value?.username}_is_ready").getValue(Boolean::class.java)
                val selection = dataSnapshot.child("${_enemy.value?.username}_selection").getValue(String::class.java)

                var selectionError = false

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


    sealed class Childs(var name: MutableStateFlow<String>){
        object Lobby : Childs(MutableStateFlow("lobby"))
        object OnlineGames : Childs(MutableStateFlow("classic_online_games"))
    }

    data class UserState(
        val isReady: Boolean,
        val selection: SelectionType?
    )
}



