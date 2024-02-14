package com.game.rockpaperscissors.presentation.screen.sign_up

import android.content.Context
import android.net.Uri
import android.util.Log
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.presentation.auth.AuthViewModel
import com.game.rockpaperscissors.presentation.auth.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: UserRepository,
    context: Context
) : AuthViewModel(context) {

    private val TAG = "currentUser"

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    val userName = MutableStateFlow("")
    val profilePicUri: MutableStateFlow<Uri> = MutableStateFlow(Uri.parse("android.resource://" + context.packageName + "/" + R.drawable.profile))


    private var storage = FirebaseStorage.getInstance()


    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun updateUserName(newUserName: String) {
        userName.value = newUserName
    }

    fun updateProfilePic(newProfilePic: Uri) {
        profilePicUri.value = newProfilePic
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

    fun onSignUpClick(goToScreen: () -> Unit, errorHandling: (String?) -> Unit, onValueChange: (Boolean) -> Unit = {}) {
        launchCatching {
            onValueChange(true)

            Log.d(TAG, "start sign up")

            var errorMessage: String? = null


            if (password.value != confirmPassword.value) {
                errorMessage = "Passwords do not match"
                errorHandling(errorMessage)
                onValueChange(false)
                Log.d(TAG, "Passwords do not match")

                throw Exception("Passwords do not match")
            }

            if(userName.value == ""){
                errorMessage = "Given String is Empty User Name"
                errorHandling(errorMessage)
                onValueChange(false)
                Log.d(TAG, "Given String is Empty User Name")

                throw Exception("Given String is Empty User Name")

            }

            if(containsAnyLetter(userName.value, listOf('@', '<', '>', '"', '^', '°', '#', '$', '%', '&'))){
                errorMessage = "user name contains illegible letters"
                errorHandling(errorMessage)
                onValueChange(false)
                throw Exception(errorMessage)
            }

            checkIfExistsUserName(userName.value){ it ->
                if(it == null){
                    launchCatching {
                        val res = accountService.signUpEMail(
                            email.value,
                            password.value,
                            userName.value,
                            null
                        )

                        res.errorMessage?.let { Log.d("currentUser --SUVM", it) }

                        accountService.updateDisplayName(userName = userName.value)
                        onValueChange(false)

                        uploadProfilePic{image, error ->
                            if(error == null){
                                launchCatching {
                                    accountService.updateProfilePic(image ?:"")
                                }

                                upLoadUserName(res.data?.userId ?:"", image ?:""){errorM ->
                                    if(errorM == null) {
                                        errorMessage = res.errorMessage
                                        errorHandling(errorMessage)

                                        if (res.errorMessage == null) {
                                            goToScreen()

                                            val userName = Firebase.auth.currentUser?.displayName ?: return@upLoadUserName
                                            setNotificationToken(userName)
                                        }
                                    }else{
                                        errorMessage = "Please Tray again"
                                        errorHandling(errorMessage)
                                    }
                                }
                            }else{
                                errorMessage = "Please Tray again"
                                errorHandling(errorMessage)
                            }
                        }
                    }
                }else{
                    errorHandling(it)
                }
            }
        }
    }

    private val database = Firebase.database(context.getString(R.string.firebase_realtime_database)).reference


    private fun setNotificationToken(userName: String){

        val notificationTokenRef = database.child("users").child(userName).child("notification_token")

        getNotificationToken {token ->
            notificationTokenRef.setValue(token)
        }
    }


    private fun checkIfExistsUserName(userName: String, callback: (String?) -> Unit){
        val usersRef = database.child("users")
        Log.d(TAG, "start checking Username")
        usersRef.child(userName).get()
            .addOnSuccessListener {
                if(it.exists()){
                    Log.d(TAG, "User Name already exists")
                    callback("User Name already exists")

                }
                else{
                    Log.d(TAG, "Successful checked Username")
                    callback(null)

                }
            }
            .addOnCanceledListener {
                Log.d(TAG, "connecting error")
                callback("connecting error")

            }
    }


    private fun upLoadUserName(userId: String, profilePic: String, callback: (String?) -> Unit){

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

        userRef.child("profilePicture").setValue(profilePic)
            .addOnSuccessListener {
                callback(null)
            }
            .addOnCanceledListener {

                callback("Fail")
            }

        userRef.child("email").setValue(email.value)
            .addOnSuccessListener {
                callback(null)
            }
            .addOnCanceledListener {

                callback("Fail")
            }
    }


    private fun uploadProfilePic(callback: (uri: String?, error: String?) -> Unit){
        if(profilePicUri.value == null){
            callback(null, "profilePic is null")
            Log.d(TAG, "profilePic is null")
            return

        }


        val storageRef = storage.getReference("users/profilePics/${userName.value}")

        storageRef.putFile(profilePicUri.value!!)
            .addOnSuccessListener {
                storageRef.downloadUrl
                    .addOnSuccessListener {
                        callback(it.toString(), null)
                        Log.d(TAG, "Successful get Image Uri")
                        Log.d(TAG, "Successful get Image Uri")
                        Log.d(TAG, "Successful get Image Uri")
                        Log.d(TAG, "Successful get Image Uri")
                    }
                    .addOnCanceledListener {

                        callback(null, "failed to get uri")
                    }

                Log.d(TAG, "Successful uploaded Image")
            }
            .addOnCanceledListener {
                callback(null, "failed to upload")
            }

    }

    fun containsAnyLetter(inputString: String, lettersToCheck: List<Char>): Boolean {
        for (letter in lettersToCheck) {
            if (letter in inputString) {
                return true
            }
        }
        return false
    }

}
















