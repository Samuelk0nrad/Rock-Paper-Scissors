package com.game.rockpaperscissors.presentation.screen.friends

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
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class FriendsViewModel @Inject constructor (
    context: Context
) : ViewModel() {

    private val TAG = "FriendsViewModelFirebase"

    private val database: DatabaseReference = Firebase.database(context.getString(R.string.firebase_realtime_database)).reference
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

    private val _allFriends: MutableStateFlow<List<UserData>> = MutableStateFlow(emptyList())
    val allFriends = _allFriends.asStateFlow()

    private val _onlineFriends: MutableStateFlow<List<UserData>> = MutableStateFlow(emptyList())
    val onlineFriends = _onlineFriends.asStateFlow()

    private val _requestsFriends: MutableStateFlow<List<UserData>> = MutableStateFlow(emptyList())
    val requestsFriends = _requestsFriends.asStateFlow()

    private val _addFriend: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val addFriend = _addFriend.asStateFlow()


    private val _userName: MutableStateFlow<String> = MutableStateFlow("")
    val userName = _userName.asStateFlow()


    fun getAllFriends(callback:(List<UserData>, String?) -> Unit){
        if(_player.value == null){
            callback(emptyList(), "no Player")
            Log.d(TAG, "no Player")
            return
        }else if(_player.value?.username == null){
            callback(emptyList(), "no Player")
            Log.d(TAG, "no Player")
            return
        }

        val userFriends = database.child("users").child(_player.value?.username ?:"").child("friends")


        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val friends: MutableList<UserData> = emptyList<UserData>().toMutableList()

                _allFriends.value = emptyList()
                _onlineFriends.value = emptyList()


                for(player in dataSnapshot.children){
                    getPlayerData(player.key?:""){user, onlineState, _ ->
                        if(user != null) {
                            friends += user
                            addAllFriend(user)
                            if(onlineState == true){
                                addOnlineFriend(user)
                            }
                        }
                    }
                }

                Log.d(TAG, "get ${dataSnapshot.children.count()} players")

                callback(friends, null)

                Log.d(TAG, "saved ${_allFriends.value.count()} players")


                return
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList(), error.message)
            }
        }

        val query: Query = userFriends.orderByChild("addTime")

        query.addListenerForSingleValueEvent(valueEventListener)
    }


    fun getRequestsFriends(callback:(List<UserData>, String?) -> Unit){
        if(_player.value == null){
            callback(emptyList(), "no Player")
            Log.d(TAG, "no Player")
            return
        }else if(_player.value?.username == null){
            callback(emptyList(), "no Player")
            Log.d(TAG, "no Player")
            return
        }

        val userFriends = database.child("users").child(_player.value?.username ?:"").child("requests")


        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val friends: MutableList<UserData> = emptyList<UserData>().toMutableList()

                _requestsFriends.value = emptyList()

                for(player in dataSnapshot.children){
                    getPlayerData(player.key?:""){user, _, _ ->
                        if(user != null) {
                            friends += user
                            addRequestsFriends(user)

                        }
                    }
                }

                Log.d(TAG, "get ${dataSnapshot.children.count()} players")

                callback(friends, null)

                Log.d(TAG, "saved ${_allFriends.value.count()} players")


                return
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList(), error.message)
            }
        }

        val query: Query = userFriends.orderByChild("addTime")

        query.addListenerForSingleValueEvent(valueEventListener)
    }



    fun getPlayerData(userName: String, callback: (UserData?, Boolean?, String?) -> Unit){

        val userRef = database.child("users").child(userName)

        userRef.get()
            .addOnSuccessListener {
                Log.d(TAG, "get ${it.child("userId").getValue(String::class.java)}")

                val user = UserData(
                    userId = it.child("userId").getValue(String::class.java) ?:"",
                    email = null,
                    username = userName,
                    profilePictureUrl = it.child("profilePicture").getValue(String::class.java)
                )

                val onlineState = it.child("onlineState").getValue(Boolean::class.java)

                callback(user, onlineState, null)
            }


    }

    fun addAllFriend(user: UserData){
        val tempList = _allFriends.value.toMutableList()
        tempList.add(user)
        _allFriends.value = tempList
    }

    fun addOnlineFriend(user: UserData){
        val tempList = _allFriends.value.toMutableList()
        tempList.add(user)
        _onlineFriends.value = tempList
    }

    fun addRequestsFriends(user: UserData){
        val tempList = _allFriends.value.toMutableList()
        tempList.add(user)
        _requestsFriends.value = tempList
    }







    fun request(userName: String, callback: (Boolean) -> Unit){
        if(_player.value == null){
            Log.d(TAG, "no Player")

            return
        }else if(_player.value?.username == null){
            Log.d(TAG, "no Player")
            return
        }

        val requestRef = database.child("users").child(userName).child("requests")

        requestRef.child(_player.value?.username ?: "").child("addTime").setValue(ServerValue.TIMESTAMP)
            .addOnSuccessListener {
                callback(true)
            }
    }

    fun exceptRequest(userName: String){
        if(_player.value == null){
            Log.d(TAG, "no Player")
            return
        }else if(_player.value?.username == null || _player.value?.username == ""){
            Log.d(TAG, "no Player")
            return
        }
        if(userName == ""){
            Log.d(TAG, "no Player")
            return
        }


        val userFriendsRef = database.child("users").child(userName).child("friends")
        val playerFriendsRef = database.child("users").child(_player.value?.username ?: "").child("friends")

        userFriendsRef.child(_player.value?.username ?: "").child("addTime").setValue(ServerValue.TIMESTAMP)
        playerFriendsRef.child(userName).child("addTime").setValue(ServerValue.TIMESTAMP)
            .addOnSuccessListener {
                getAllFriends{_, _ -> }
                getRequestsFriends{_, _ -> }

            }



        val requestRef = database.child("users").child(_player.value?.username ?:"").child("requests")

        requestRef.child(userName).removeValue()

    }

    private fun userExists(callback: (Boolean) -> Unit){
        val userRef = database.child("users").child(_userName.value)

        userRef.get()
            .addOnSuccessListener {
                callback(it.exists())
            }
    }

    fun onClickAddFriendButton(){
        _addFriend.update {
            true
        }
    }

    fun onCancelAddFriendButton(){
        _addFriend.update {
            false
        }

        _userName.update {
            ""
        }
    }

    fun onClickAddFriend(errorHandling: (String?) -> Unit){
        userExists {
            if(!it){
                errorHandling("User Don't exists")
            }else{
                request(_userName.value){success ->
                    if(!success){
                        errorHandling("something went wrong")
                    }else{
                        errorHandling(null)
                    }
                }
            }
        }
    }


    fun updateUserName(userName: String){
        _userName.update {
            userName
        }
    }



}





