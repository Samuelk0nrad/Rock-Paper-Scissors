package com.game.rockpaperscissors.data.viewModel


import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    }

    private fun checkingFriendRequest(){
        if(_user.value == null || _user.value?.username == null){
            return
        }

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

                                    is InAppNotification.PlayWithFriend -> TODO()
                                }
                            }
                            if (!exists) {
                                _state.update {
                                    InAppNotificationState(
                                        display = true,
                                        notification =
                                        it.notification + InAppNotification.FriendRequest(friendName)
                                    )
                                }
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

    private fun onDismissFriendRequest(userName: String){
        val friendRef = database.child("users").child(_user.value?.username!!).child("requests").child(userName)

        friendRef.child("is_showed").setValue(true)
    }

    private fun onDismissPlayRequest(userName: String, gameId: String){
        TODO()
    }

    fun onDismissNotification(inAppNotification: InAppNotification){
        when(inAppNotification){
            is InAppNotification.FriendRequest -> {
                onDismissFriendRequest(inAppNotification.userName)
            }
            is InAppNotification.PlayWithFriend -> {

                onDismissPlayRequest(inAppNotification.userName, inAppNotification.gameId)

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
            is InAppNotification.PlayWithFriend -> TODO()
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
}

data class InAppNotificationState(
    val display: Boolean = false,
    val notification: List<InAppNotification> = emptyList()
)

sealed interface InAppNotification{

    data class FriendRequest(val userName: String) : InAppNotification
    data class PlayWithFriend(val userName: String, val gameId: String) : InAppNotification
}





