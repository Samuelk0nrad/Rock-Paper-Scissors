package com.thephoenix.rockpaperscissors.presentation.screen.sign_up


import android.content.Context
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.thephoenix.rockpaperscissors.R
import com.thephoenix.rockpaperscissors.data.Screen
import com.thephoenix.rockpaperscissors.presentation.screen.CustomTextFiled
import com.thephoenix.rockpaperscissors.ui.theme.Oswald
import com.thephoenix.rockpaperscissors.ui.theme.appColor
import com.google.firebase.auth.FirebaseAuth

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

    var confirmPasswordError by remember {
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

    var confinePasswordEText by remember {
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

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri ->
            if(uri != null){
                viewModel.updateProfilePic(uri)
//                fileName = saveImageToInternalStorage(context = context, uri = uri, fileName = "profile_picture")
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
                    text = stringResource(id = R.string.sign_up),
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
                        Icon(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(13.dp),
                            imageVector = Icons.Rounded.CameraAlt,
                            contentDescription = "Camara",
                            tint = appColor.background
                        )

                        AsyncImage(
                            model = profilePic.value,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

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
                    placeholder = stringResource(id = R.string.user_name),
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
                    lineColor = if(eMailError) appColor.red else appColor.onBackground,
                    keyboardOptions =  KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
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
                    placeholder = stringResource(id = R.string.password),
                    startPadding = 0.dp,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    lineColor = if(passwordError) appColor.red else appColor.onBackground
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = passwordEText,
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
                        confirmPasswordError = false
                        confinePasswordEText = ""
                    },
                    placeholder = stringResource(id = R.string.confirm_password),
                    startPadding = 0.dp,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    lineColor = if(confirmPasswordError) appColor.red else appColor.onBackground
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = confinePasswordEText,
                    fontSize = 12.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.ExtraLight,
                    color = appColor.red
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorText,
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
                                    when (it) {
                                        "Given String is Empty User Name" -> {
                                            userNameError = true
                                            userNameEText = ""
                                        }

                                        "Passwords do not match" -> {
                                            confirmPasswordError = true
                                            confinePasswordEText = it
                                        }

                                        "The email address is already in use by another account." -> {
                                            eMailError = true
                                            eMailEText = "The email address is already in use"
                                        }

                                        "Given String is empty or null" -> {
                                            if (email.value == "") {
                                                eMailError = true
                                                eMailEText = ""
                                            }

                                            if (password.value == "") {
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
                                            passwordEText =
                                                "Password should be at least 6 characters"
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

                                        "User Name already exists" -> {
                                            userNameError = true
                                            userNameEText = it
                                        }

                                        "user name contains illegible letters" -> {
                                            userNameError = true
                                            userNameEText = it
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
                        text = stringResource(id = R.string.sign_up),
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
                        text = stringResource(id = R.string.you_already_have_an_account),
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
                        text = stringResource(id = R.string.login),
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
                        text = stringResource(id = R.string.login),
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Light,
                        color = appColor.onSecondaryContainer,
                        letterSpacing = 1.28.sp,
                        textAlign = TextAlign.Center,
                    )
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