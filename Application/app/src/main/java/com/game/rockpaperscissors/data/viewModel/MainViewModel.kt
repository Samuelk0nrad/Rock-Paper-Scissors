package com.game.rockpaperscissors.data.viewModel


import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    context: Context
): ViewModel() {

    private val TAG = "MainViewModelLog"


    private val database = Firebase.database(context.getString(R.string.firebase_realtime_database)).reference

    private val _user: MutableStateFlow<UserData?> = MutableStateFlow(
        Firebase.auth.currentUser?.let {
            UserData(
                userId = it.uid,
                username = it.displayName,
                email = it.email,
                profilePictureUrl = it.photoUrl?.toString()
            )
        }
    )



    private val _state: MutableStateFlow<InAppNotificationState> = MutableStateFlow(
        InAppNotificationState()
    )
    val state = _state.asStateFlow()



    init {
        checkingFriendRequest()
        checkingPlayRequests()
    }

    private fun checkingFriendRequest(){
        if(_user.value == null || _user.value?.username == null){
            Log.d(TAG,"checkingFriendRequest return")
            return
        }
        Log.d(TAG,"! checkingFriendRequest !not! return")

        val friendRef = database.child("users").child(_user.value?.username!!).child("requests")


        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for (friends in dataSnapshot.children){

                        if(!friends.child("is_showed").exists() || friends.child("is_showed").getValue(Boolean::class.java) != true) {


                            val friendName = friends.key ?: ""
                            var exists = false
                            for (note in _state.value.notification) {
                                when (note) {
                                    is InAppNotification.FriendRequest -> {
                                        if (note.userName == friendName) {
                                            exists = true
                                        }
                                    }

                                    is InAppNotification.PlayWithFriend -> {}
                                }
                            }
                            if (!exists) {
                                addNotification(InAppNotification.FriendRequest(friendName))
                            }
                        }
                    }
                    Log.d(TAG,"size of friends: ${_state.value.notification.size}, exists")

                }else{
                    Log.d(TAG,"size of friends: ${_state.value.notification.size}, not exists")

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }


        Log.d(TAG,"query 1")
        friendRef.addValueEventListener(valueEventListener)
    }

    private fun checkingPlayRequests(){
        if(  _user.value == null || _user.value?.username == null || _user.value?.username == ""){
            return
        }


        val playRequestRef = database.child("users").child(_user.value?.username!!).child("play_request")

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (gameRequest in dataSnapshot.children){
                    val playerName = gameRequest.getValue(String::class.java) ?:""
                    val gameId = gameRequest.key ?:""
                    var exists = gameId == "" || playerName == ""
                    for (note in _state.value.notification) {
                        when (note) {
                            is InAppNotification.FriendRequest -> {}

                            is InAppNotification.PlayWithFriend -> {
                                if(note.gameId == gameId){
                                    exists = true
                                }
                            }
                        }
                    }
                    if (!exists) {
                        addNotification(InAppNotification.PlayWithFriend(playerName, gameId))
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        playRequestRef.addValueEventListener(valueEventListener)

    }




    private fun onDismissFriendRequest(userName: String){
        if(  _user.value == null || _user.value?.username == null || _user.value?.username == ""){
            return
        }

        val friendRef = database.child("users").child(_user.value?.username!!).child("requests").child(userName)

        friendRef.child("is_showed").setValue(true)
    }

    private fun onDismissPlayRequest(gameId: String){
        if(  _user.value == null || _user.value?.username == null || _user.value?.username == ""){
            return
        }

        val lobbyRef = database.child("reserved_lobby").child(gameId)
        val playRequestRef = database.child("users").child(_user.value?.username!!).child("play_request").child(gameId)

        lobbyRef.removeValue()
        playRequestRef.removeValue()
    }

    fun onDismissNotification(inAppNotification: InAppNotification){
        when(inAppNotification){
            is InAppNotification.FriendRequest -> {
                onDismissFriendRequest(inAppNotification.userName)
            }
            is InAppNotification.PlayWithFriend -> {
                onDismissPlayRequest(inAppNotification.gameId)

            }
        }

        removeNotification(inAppNotification)
    }

    private fun removeNotification(inAppNotification: InAppNotification){
        _state.update {
            val notifications: MutableList<InAppNotification> = it.notification.toMutableList()
            notifications.remove(inAppNotification)

            InAppNotificationState(
                notifications.isNotEmpty(),
                notifications
            )
        }
    }

    private fun addNotification(inAppNotification: InAppNotification){
        _state.update {
            val notifications: MutableList<InAppNotification> = it.notification.toMutableList()
            if(notifications.isEmpty()) deleteDelay()
            notifications.add(inAppNotification)

            InAppNotificationState(
                notifications.isNotEmpty(),
                notifications
            )
        }
    }

    fun onClickAction(inAppNotification: InAppNotification, action: Int){
        when(inAppNotification){
            is InAppNotification.FriendRequest -> {
                when(action){
                    1 ->{
                        acceptFriendRequest(inAppNotification.userName)
                    }
                    2 ->{
                        notAcceptFriendRequest(inAppNotification.userName)
                    }
                }
            }
            is InAppNotification.PlayWithFriend -> {
                acceptPlayRequest(inAppNotification.userName)
            }
        }

        removeNotification(inAppNotification)
    }


    private fun acceptFriendRequest(userName: String){
        if(_user.value == null){
            Log.d(TAG, "no Player")
            return
        }else if(_user.value?.username == null || _user.value?.username == ""){
            Log.d(TAG, "no Player")
            return
        }
        if(userName == ""){
            Log.d(TAG, "no Player")
            return
        }


        val userFriendsRef = database.child("users").child(userName).child("friends")
        val playerFriendsRef = database.child("users").child(_user.value?.username ?: "").child("friends")

        userFriendsRef.child(_user.value?.username ?: "").child("addTime").setValue(ServerValue.TIMESTAMP)
        playerFriendsRef.child(userName).child("addTime").setValue(ServerValue.TIMESTAMP)



        val requestRef = database.child("users").child(_user.value?.username ?:"").child("requests")

        requestRef.child(userName).removeValue()
    }

    private fun notAcceptFriendRequest(userName: String){
        if(_user.value == null || _user.value?.username == null || _user.value?.username == "" || userName == ""){
            Log.d(TAG, "no Player")
            return
        }

        val requestRef = database.child("users").child(_user.value?.username ?:"").child("requests")

        requestRef.child(userName).removeValue()
    }

    private fun acceptPlayRequest(userName: String){
        if(_user.value == null || _user.value?.username == null || _user.value?.username == "" || userName == ""){
            return
        }

        val requestRef = database.child("users").child(userName).child("play_request")
        requestRef.child(_user.value?.username!!).removeValue()

    }


    private fun deleteDelay(){

        viewModelScope.launch {
            delay(500)


            var toDelete = false
            for(note in _state.value.notification){
                if(note.delete){
                    toDelete = true
                }
            }


            while(toDelete){

                Log.d("MainViewModelLog", "delete: ${_state.value.notification[0]}")
                delay(5 * 1000)

                var i = 0
                var toDeleteNotification: InAppNotification? = null
                var delete = false
                while (i < _state.value.notification.size && !delete){
                    if (_state.value.notification[i].delete){
                        delete = true
                        toDeleteNotification = _state.value.notification[i]
                    }
                    i++
                }

                if(delete){
                    onDismissNotification(toDeleteNotification!!)
                }


                toDelete = false
                for(note in _state.value.notification){
                    if(note.delete){
                        toDelete = true
                    }
                }
            }
        }
    }
}

data class InAppNotificationState(
    val display: Boolean = false,
    val notification: List<InAppNotification> = emptyList()
)

sealed class InAppNotification private constructor(val delete: Boolean){

    data class FriendRequest(val userName: String) : InAppNotification(true)
    data class PlayWithFriend(val userName: String, val gameId: String) : InAppNotification(false)
}





