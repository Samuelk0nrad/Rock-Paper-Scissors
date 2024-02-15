 package com.game.rockpaperscissors

import android.content.Context
import com.game.rockpaperscissors.presentation.auth.third_party_sign_in.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

class PushNotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var context: Context

    private val userName = Firebase.auth.currentUser?.displayName

    private val database: DatabaseReference? =
        if(this::context.isInitialized) Firebase.database(context.getString(R.string.firebase_realtime_database)).reference
        else null



    override fun onNewToken(token: String) {
        super.onNewToken(token)

        if(userName == null || userName == "" || database == null){
            return
        }

        val notificationTokenRef = database.child("users").child(userName).child("notification_token")
        notificationTokenRef.setValue(token)
    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)


    }


}