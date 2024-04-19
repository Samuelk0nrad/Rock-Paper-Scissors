package com.thephoenix.rockpaperscissors.presentation.screen.edit_profile

import android.content.Context
import android.net.Uri
import android.util.Log
import com.thephoenix.rockpaperscissors.R
import com.thephoenix.rockpaperscissors.presentation.auth.AuthViewModel
import com.thephoenix.rockpaperscissors.presentation.auth.UserRepository
import com.thephoenix.rockpaperscissors.presentation.auth.third_party_sign_in.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    context: Context
) : AuthViewModel(context) {
    private val TAG = "currentUser"

    private val database = Firebase.database(context.getString(R.string.firebase_realtime_database)).reference
    private var storage = FirebaseStorage.getInstance()


    private val _userData: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val userData = _userData.asStateFlow()

    val userName = MutableStateFlow("")
    val email = MutableStateFlow("")
    val profilePic = MutableStateFlow<Uri?>(null)


    init {
        val user = Firebase.auth.currentUser
        _userData.update {
            user?.let { it1 ->
                Log.d("currentUser ------","${it1.email}")
                UserData(
                    userId = it1.uid,
                    email = it1.email,
                    username = it1.displayName,
                    profilePictureUrl = it1.photoUrl?.toString()
                )
            }
        }
        userName.update {
            _userData.value?.username ?: ""
        }
        email.update {
            _userData.value?.email ?: ""
        }

    }

    fun updateUserName(newUserName: String){
        userName.update {
            newUserName
        }
    }

    fun updateProfilePic(newProfilePic: Uri){
        this.profilePic.update {
            newProfilePic
        }
    }

    fun updateEmail(newEmail: String){
        email.update {
            newEmail
        }
    }




    fun onClickSave(goToScreen: () -> Unit, onValueChange: (Boolean) -> Unit = {}) {
        if (_userData.value == null) {
            return
        }

        onValueChange(true)
        if (profilePic.value != null) {
            Log.d("currentUser", "profilePic.Change")
            uploadProfilePic(_userData.value?.userId!!) { uri ->
                if (uri != null) {
                    launchCatching {
                        Log.d(TAG, "Image url is ${Uri.parse(uri)}")
                        userRepository.updateProfilePic(uri)
                        val profilePicTest = Firebase.auth.currentUser?.photoUrl
                        Log.d(TAG, "Successful update image: $profilePicTest")

                        onValueChange(true)
                        if (email.value != _userData.value?.email) {
                            Log.d("currentUser", "email.Change")
                            userRepository.updateEmail(email.value)
                            uploadEmail(_userData.value?.userId!!) {
                            }
                        }
                        if (userName.value != _userData.value?.username) {
                            Log.d("currentUser", "userName.Change")
                            userRepository.updateDisplayName(userName.value)
                            uploadUserName(_userData.value?.userId!!) {
                            }
                        }

                        onValueChange(false)
                        goToScreen()
                    }
                }else{
                    onValueChange(false)
                }
            }
        }else{
            launchCatching {
                onValueChange(true)
                if (email.value != _userData.value?.email) {
                    Log.d("currentUser", "email.Change")
                    userRepository.updateEmail(email.value)
                    uploadEmail(_userData.value?.userId!!) {
                    }
                }
                if (userName.value != _userData.value?.username) {
                    Log.d("currentUser", "userName.Change")
                    userRepository.updateDisplayName(userName.value)
                    uploadUserName(_userData.value?.userId!!) {
                    }
                }

                onValueChange(false)
                goToScreen()
            }
        }
    }

    fun onClickChangePassword(onValueChange: (Boolean) -> Unit = {}){
        launchCatching {
            onValueChange(true)
            _userData.value?.email?.let { userRepository.resetPassword(it) }
            onValueChange(false)
        }
    }



    private fun uploadUserName(userId: String, callback: (String?) -> Unit){

        Log.d(TAG, "userId is: $userId")
        if(userId == ""){
            callback("error by Id")
            return
        }

        val userRef = database.child("users").child(userName.value)

        userRef.child("userId").setValue(userId)
            .addOnSuccessListener {
                Log.d(TAG, "Successful up Load user name")
                callback(null)
            }
            .addOnCanceledListener {
                Log.d(TAG, "failed to up Load user name")

                callback("Fail")
            }




    }

    fun uploadEmail(userId: String, callback: (String?) -> Unit){
        Log.d(TAG, "userId is: $userId")
        if(userId == ""){
            callback("error by Id")
            return
        }

        val userRef = database.child("users").child(userName.value)


        userRef.child("email").setValue(email.value)
            .addOnSuccessListener {
                callback(null)
            }
            .addOnCanceledListener {

                callback("Fail")
            }
    }



    private fun uploadProfilePic(userId: String, callback: (uri: String?) -> Unit){
        if(profilePic.value == null){
            callback(null)
            Log.d(TAG, "profilePic is null")
            return
        }
        Log.d(TAG, "userId is: $userId")
        if(userId == ""){
            callback(null)
            return
        }


        val storageRef = storage.getReference("users/profilePics/${userName.value}")

        storageRef.putFile(profilePic.value!!)
            .addOnSuccessListener {

//                Log.d(TAG, "Successful uploaded Image")

                storageRef.downloadUrl
                    .addOnSuccessListener {url ->
                        callback(url.toString())
                        Log.d(TAG, "Successful get Image Uri")
                        Log.d(TAG, "Successful get Image Uri")
                        Log.d(TAG, "Successful get Image Uri")
                        Log.d(TAG, "Successful get Image Uri")



//                        val userRef = database.child("users").child(userName.value)

//                        userRef.child("profilePicture").setValue(url)
//                            .addOnSuccessListener {
//                                callback(null)
//
//                            }
//                            .addOnCanceledListener {
//
//                                callback("Fail")
//                            }

                    }
                    .addOnCanceledListener {

                        callback(null)
                    }

            }
            .addOnCanceledListener {
                callback(null)
            }

    }


}












