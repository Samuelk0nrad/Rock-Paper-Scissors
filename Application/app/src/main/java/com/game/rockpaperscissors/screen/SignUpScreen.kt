package com.game.rockpaperscissors.screen


import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.game.rockpaperscissors.presentation.auth.SignUpViewModel

import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor
import com.google.firebase.auth.FirebaseAuth
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    auth: FirebaseAuth,
    context: Context,
    viewModel: SignUpViewModel,
) {

    var isLoading by remember {
        mutableStateOf(false)
    }


    val user = auth.currentUser

    var userNameError by remember {
        mutableStateOf(false)
    }

    var eMailError by remember {
        mutableStateOf(false)
    }

    var passwordError by remember {
        mutableStateOf(false)
    }

    var confiermPasswordError by remember {
        mutableStateOf(false)
    }

    var errorText by remember {
        mutableStateOf("")
    }

    var userNameEText by remember {
        mutableStateOf("")
    }

    var eMailEText by remember {
        mutableStateOf("")
    }

    var passwordEText by remember {
        mutableStateOf("")
    }

    var confirePasswordEText by remember {
        mutableStateOf("")
    }



    if(user != null) {
        navController.navigate(Screen.LogedAlreadyIn.route)
    }



    //loginResult = firebaseViewModel.loginResult.value

    val email = viewModel.email.collectAsState()
    val password = viewModel.password.collectAsState()
    val confirmPassword = viewModel.confirmPassword.collectAsState()
    val userName = viewModel.userName.collectAsState()
    val profilePic = viewModel.profilePicUri.collectAsState()




/*    when(loginResult){
//        is UserResult.Error -> {
//            when((loginResult as UserResult.Error).errorMessage){
//                "The email address is already in use by another account." -> {
//                    Log.d("currentUser -- login Result", (loginResult as UserResult.Error).errorMessage)
//
//                    eMailError = true
//                    eMailEText = "The email address is already in use"
//                }
//                "Given String is empty or null" -> {
//                    Log.d("currentUser -- login Result", (loginResult as UserResult.Error).errorMessage)
//                    if(eMail == ""){
//                        eMailError = true
//                        eMailEText = ""
//                    }
//
//                    if(password == ""){
//                        passwordError = true
//                        passwordEText = ""
//                    }
//                }
//                "The email address is badly formatted." -> {
//                    Log.d("currentUser -- login Result", (loginResult as UserResult.Error).errorMessage)
//
//
//                    eMailError = true
//                    eMailEText = "The email address is badly formatted."
//
//                }
//                "The given password is invalid. [ Password should be at least 6 characters ]" -> {
//                    Log.d("currentUser -- login Result", (loginResult as UserResult.Error).errorMessage)
//
//                    passwordError = true
//                    passwordEText = "Password should be at least 6 characters"
//                }
//                "The user account has been disabled by an administrator." -> {
//                    Log.d("currentUser -- login Result", (loginResult as UserResult.Error).errorMessage)
//
//                    errorText = "The user account has been disabled by an administrator."
//                }
//                "The provided custom claim attributes are invalid." -> {
//                    Log.d("currentUser -- login Result", (loginResult as UserResult.Error).errorMessage)
//
//                    errorText = (loginResult as UserResult.Error).errorMessage
//                }
//
//                "This operation is not allowed. You must enable this service in the console." -> {
//                    Log.d("currentUser -- login Result", (loginResult as UserResult.Error).errorMessage)
//
//                    errorText = (loginResult as UserResult.Error).errorMessage
//                }
//
//                "We have blocked all requests from this device due to unusual activity. Try again later." -> {
//                    Log.d("currentUser -- login Result", (loginResult as UserResult.Error).errorMessage)
//
//                    errorText = (loginResult as UserResult.Error).errorMessage
//                }
//
//                "A network error (such as timeout, interrupted connection, or unreachable host) has occurred." -> {
//                    Log.d("currentUser -- login Result", (loginResult as UserResult.Error).errorMessage)
//
//                    errorText = (loginResult as UserResult.Error).errorMessage
//                }
//
//                "An unknown error occurred." -> {
//                    Log.d("currentUser -- login Result", (loginResult as UserResult.Error).errorMessage)
//
//                    errorText = (loginResult as UserResult.Error).errorMessage
//                }
//            }
//
//
//        }
//        is UserResult.Success -> {
//
//            Log.d("currentUser -- login Result", "UserResult.Success")
//
//        }
//        UserResult.UnitSuccess -> {
//            Log.d("currentUser -- login Result", "UserResult.UnitSuccess")
//        }
//        null -> {
//            Log.d("currentUser -- login Result", "null")
//        }
//    }*/




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
                viewModel.updateProfilePic(uri)
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
                .padding(top = 0.dp, start = 31.dp, end = 31.dp),
        ) {

            item {
                Spacer(modifier = Modifier.height(94.dp))
            }

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
                    userName.value,
                    onValueChange = {
                        viewModel.updateUserName(it)
                        userNameError = false
                        userNameEText = ""

                    },
                    placeholder = "User Name",
                    startPadding = 0.dp,
                    lineColor = if(userNameError) appColor.red else appColor.onBackground
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = userNameEText,
                    fontSize = 12.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.ExtraLight,
                    color = appColor.red
                )

                Spacer(modifier = Modifier.height(33.dp))


                CustomTextFiled(
                    email.value,
                    onValueChange = {

                        viewModel.updateEmail(it)
                        eMailError = false
                        eMailEText = ""

                    },
                    placeholder = "E-Mail",
                    startPadding = 0.dp,
                    lineColor = if(eMailError) appColor.red else appColor.onBackground
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = eMailEText,
                    fontSize = 12.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.ExtraLight,
                    color = appColor.red
                )

                Spacer(modifier = Modifier.height(33.dp))


                CustomTextFiled(
                    password.value,
                    onValueChange = {
                        viewModel.updatePassword(it)
                        passwordError = false
                        passwordEText = ""
                    },
                    placeholder = "password",
                    startPadding = 0.dp,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    lineColor = if(passwordError) appColor.red else appColor.onBackground
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorText,
                    fontSize = 12.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.ExtraLight,
                    color = appColor.red
                )


                Spacer(modifier = Modifier.height(33.dp))


                CustomTextFiled(
                    confirmPassword.value,
                    onValueChange = {
                        viewModel.updateConfirmPassword(it)
                        confiermPasswordError = false
                        confirePasswordEText = ""
                    },
                    placeholder = "Confirm Password",
                    startPadding = 0.dp,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    lineColor = if(confiermPasswordError) appColor.red else appColor.onBackground
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = confirePasswordEText,
                    fontSize = 12.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.ExtraLight,
                    color = appColor.red
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = passwordEText,
                    fontSize = 18.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    color = appColor.red
                )

                Spacer(modifier = Modifier.height(16.dp))

            }

            item {

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(appColor.secondaryContainer)
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onSignUpClick(
                                goToScreen = {
                                    navController.navigate(Screen.LogedAlreadyIn.route)
                                },
                                errorHandling = {
                                    when(it){
                                        "Passwords do not match" -> {
                                            confiermPasswordError = true
                                            confirePasswordEText = it
                                        }
                                        "The email address is already in use by another account." -> {
                                            eMailError = true
                                            eMailEText = "The email address is already in use"
                                        }
                                        "Given String is empty or null" -> {
                                            if(email.value == ""){
                                                eMailError = true
                                                eMailEText = ""
                                            }

                                            if(password.value == ""){
                                                passwordError = true
                                                passwordEText = ""
                                            }
                                        }
                                        "The email address is badly formatted." -> {
                                            eMailError = true
                                            eMailEText = "The email address is badly formatted."

                                        }
                                        "The given password is invalid. [ Password should be at least 6 characters ]" -> {

                                            passwordError = true
                                            passwordEText = "Password should be at least 6 characters"
                                        }
                                        "The user account has been disabled by an administrator." -> {

                                            errorText = it
                                        }
                                        "The provided custom claim attributes are invalid." -> {


                                            errorText = it
                                        }

                                        "This operation is not allowed. You must enable this service in the console." -> {


                                            errorText = it
                                        }

                                        "We have blocked all requests from this device due to unusual activity. Try again later." -> {


                                            errorText = it
                                        }

                                        "A network error (such as timeout, interrupted connection, or unreachable host) has occurred." -> {

                                            errorText = it
                                        }

                                        "An unknown error occurred." -> {

                                            errorText = it
                                        }
                                        null -> {}
                                        else -> {
                                            errorText = it
                                        }
                                    }
                                },
                                onValueChange = { isLoading = it }
                            )
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

                SocialLogin{
                    navController.navigate(Screen.LogedAlreadyIn.route)
                }
            }
        }

        if(isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor.background.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}


