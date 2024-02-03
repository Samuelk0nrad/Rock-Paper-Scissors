package com.game.rockpaperscissors.presentation.screen.friends

import android.content.Context
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class FriendsViewModle @Inject constructor (
    context: Context
) {

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


    fun getAllFriends(callback:(List<UserData>, String?) -> Unit){
        if(_player.value == null){
            callback(emptyList(), "no Player")
            return
        }else if(_player.value?.username == null){
            callback(emptyList(), "no Player")
            return
        }

        val userFriends = database.child("users").child(_player.value?.username ?:"").child("friends")


        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val friends: MutableList<UserData> = emptyList<UserData>().toMutableList()
                for(player in dataSnapshot.children){
                    getPlayerData(player.key?:""){user, error ->
                        if(user != null)
                            friends += user
                    }
                }

                callback(friends, null)

                _allFriends.update {
                    friends
                }

                return
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList(), error.message)
            }
        }

        val query: Query = userFriends.orderByKey()

        query.addListenerForSingleValueEvent(valueEventListener)
    }


    fun getPlayerData(userName: String, callback: (UserData?, String?) -> Unit){

        val userRef = database.child("users").child(userName)

        userRef.get()
            .addOnSuccessListener {
                val user = UserData(
                    userId = it.child("userId").getValue(String::class.java) ?:"",
                    email = null,
                    username = userName,
                    profilePictureUrl = it.child("profilePicture").getValue(String::class.java)
                )

                callback(user, null)
            }


    }

}





