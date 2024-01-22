package com.game.rockpaperscissors.composable.screen


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.local.database.PlayerDataEvent
import com.game.rockpaperscissors.data.viewModel.FirebaseViewModel
import com.game.rockpaperscissors.firebase.UserResult
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor
import com.google.firebase.auth.FirebaseAuth
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun SignUpScreen(
    navController: NavController,
    auth: FirebaseAuth,
    context: Context,
    firebaseViewModel: FirebaseViewModel
) {


    val user = auth.currentUser



    when(val loginResult = firebaseViewModel.loginResult.value){
        is UserResult.Error -> {
            when(loginResult.errorMessage){
                "The email address is already in use by another account." -> {
                    Log.d("currentUser -- login Result", loginResult.errorMessage)
                }
                "Given String is empty or null" -> {
                    Log.d("currentUser -- login Result", loginResult.errorMessage)
                }
                "The email address is badly formatted." -> {
                    Log.d("currentUser -- login Result", loginResult.errorMessage)
                }
                "The given password is invalid. [ Password should be at least 6 characters ]" -> {
                    Log.d("currentUser -- login Result", loginResult.errorMessage)
                }
                "The user account has been disabled by an administrator." -> {
                    Log.d("currentUser -- login Result", loginResult.errorMessage)
                }
                "The provided custom claim attributes are invalid." -> {
                    Log.d("currentUser -- login Result", loginResult.errorMessage)
                }

                "This operation is not allowed. You must enable this service in the console." -> {
                    Log.d("currentUser -- login Result", loginResult.errorMessage)
                }

                "We have blocked all requests from this device due to unusual activity. Try again later." -> {
                    Log.d("currentUser -- login Result", loginResult.errorMessage)
                }

                "A network error (such as timeout, interrupted connection, or unreachable host) has occurred." -> {
                    Log.d("currentUser -- login Result", loginResult.errorMessage)
                }

                "An unknown error occurred." -> {
                    Log.d("currentUser -- login Result", loginResult.errorMessage)
                }
            }


        }
        is UserResult.Success -> {

        }
        UserResult.UnitSuccess -> {

        }
        null -> {

        }
    }

    if(user != null) {
        navController.navigate(Screen.LogedAlreadyIn.route)
    }



    var eMail by remember{
        mutableStateOf("")
    }

    var password by remember{
        mutableStateOf("")
    }

    var userName by remember{
        mutableStateOf("")
    }



    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var fileName: String? by remember {
        mutableStateOf(null)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri ->
            if(uri != null){
                imageUri = uri
                fileName = saveImageToInternalStorage(context = context, uri = uri, fileName = "profile_picture")
            }
        }
    )


    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .background(appColor.background)
    ){padding->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(appColor.background)
                .padding(top = 94.dp, start = 31.dp, end = 31.dp),
        ) {

            item {
                Text(
                    text = "Sign Up",
                    fontSize = 30.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = appColor.onBackground
                )

                Spacer(modifier = Modifier.height(39.dp))
            }


            item {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RoundedCornerShape(90.dp))
                            .background(appColor.onBackground)
                            .clickable {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        contentAlignment = Alignment.Center

                    ) {
                        val imageFile: File? = fileName?.let { getImage(context, it) }


                        Icon(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(13.dp),
                            imageVector = Icons.Rounded.CameraAlt,
                            contentDescription = "Camara",
                            tint = appColor.background
                        )
                        if (imageFile != null) {
                            AsyncImage(
                                model = imageFile.toUri(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(38.dp))


            }




            item {

                CustomTextFiled(
                    userName,
                    onValueChange = { userName = it },
                    placeholder = "User Name",
                    startPadding = 0.dp
                )

                Spacer(modifier = Modifier.height(43.dp))


                CustomTextFiled(
                    eMail,
                    onValueChange = { eMail = it },
                    placeholder = "E-Mail",
                    startPadding = 0.dp
                )

                Spacer(modifier = Modifier.height(43.dp))


                CustomTextFiled(
                    password,
                    onValueChange = { password = it },
                    placeholder = "password",
                    startPadding = 0.dp,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(42.dp))
            }

            item {

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(appColor.secondaryContainer)
                        .fillMaxWidth()
                        .clickable {
                            firebaseViewModel.signUpUserEmail(eMail, password)
                        }
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Sign Up",
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = appColor.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }








            item {
                Row {
                    Text(
                        text = "You already have an account? ",
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left,
                        color = appColor.onBackground,
                        letterSpacing = 0.65.sp,
                    )
                    Text(
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.LoginScreen.route)
                        },
                        text = "Login",
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Left,
                        color = appColor.onBackground,
                        letterSpacing = 0.65.sp,
                        textDecoration = TextDecoration.Underline,
                    )
                }

                Spacer(modifier = Modifier.height(23.dp))
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(33.dp),
                    contentAlignment = Alignment.Center
                ){
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(appColor.onSecondaryContainer)
                    )

                    Text(
                        modifier = Modifier
                            .background(appColor.background)
                            .padding(horizontal = 12.dp),
                        text = "OR",
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Light,
                        color = appColor.onSecondaryContainer,
                        letterSpacing = 1.28.sp,
                        textAlign = TextAlign.Center,
                    )
                }

                Spacer(modifier = Modifier.height(35.dp))

                SocialLogin()

            }
        }
    }


}

