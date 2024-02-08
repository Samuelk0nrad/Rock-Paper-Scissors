package com.game.rockpaperscissors.presentation.screen.edit_profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.R
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.presentation.screen.CustomTextFiled
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor


@Composable
fun EditProfileScreen(
    navController: NavController,
    context: Context,
    viewModel: EditProfileViewModel

) {

    val email = viewModel.email.collectAsState()
    val userName = viewModel.userName.collectAsState()


    var isLoading by remember {
        mutableStateOf(false)
    }



/*    val photoPickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickVisualMedia(),
//        onResult = {uri ->
//            if(uri != null){
//                fileName = saveImageToInternalStorage(context = context, uri = uri, fileName = "profile_picture")
//
//                onEvent(PlayerDataEvent.SetUserImage(fileName!!))
//
//            }
//        }
   )*/







    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(appColor.background)
    ){

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(89.dp)
                    .background(appColor.secondaryContainer)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 6.dp, start = 12.dp),
                    contentAlignment = Alignment.TopStart

                ) {
                    Icon(
                        modifier = Modifier
                            .size(42.dp)
                            .clickable {

                                navController.popBackStack()
                            },
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Go Back",
                        tint = appColor.onBackground
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = stringResource(id = R.string.your_profile) ,
                        fontSize = 20.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 6.dp, end = 12.dp),
                    contentAlignment = Alignment.TopEnd

                ) {
                    Icon(
                        modifier = Modifier
                            .size(42.dp)
                            .clickable {
                                viewModel.onClickSave(
                                    goToScreen = {
                                        navController.popBackStack()
                                        navController.popBackStack()
                                        navController.navigate(Screen.ProfileScreen.route)
                                    },
                                    onValueChange = { isLoading = it }
                                )
                            },
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Apply",
                        tint = appColor.onBackground
                    )
                }


            }


            Box(
                modifier = Modifier
                    .padding(top = 46.dp)
                    .height(86.dp)
                    .width(86.dp)


            ) {


                /*val imageFile: File? = getImage(context, fileName)


                Image(
                    contentDescription = "Profile",
                    imageVector = Icons.Rounded.Person,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(100.dp))
                        .background(appColor.onBackground)

                )
                if (imageFile != null) {
                    AsyncImage(
                        model = imageFile.toUri(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                            .clip(RoundedCornerShape(100.dp)),
                        contentScale = ContentScale.Crop
                    )
                }*/
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {


                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .clickable {
//                                photoPickerLauncher.launch(
//                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//                                )
                            }
                            .background(appColor.background),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(22.dp),
                            imageVector = Icons.Rounded.CameraAlt,
                            contentDescription = "Camera",
                            tint = appColor.onBackground
                        )
                    }
                }
            }
        }




        Spacer(modifier = Modifier.height(65.dp))




        CustomTextFiled(
            value = userName.value,
            onValueChange = {
                viewModel.updateUserName(it)
            },
            placeholder = stringResource(id = R.string.user_name)
        )

        Spacer(modifier = Modifier.height(53.dp))


        CustomTextFiled(
            value = email.value,
            onValueChange = {
                viewModel.updateEmail(it)
            },
            keyboardOptions =  KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            placeholder = stringResource(id = R.string.e_mail)
        )


        Spacer(modifier = Modifier.height(63.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .padding(horizontal = 45.dp)
                .clip(RoundedCornerShape(57.dp))
                .background(appColor.secondaryContainer)
                .clickable {
                    viewModel.onClickChangePassword(
                        onValueChange = {
                            isLoading = it
                        }
                    )

                    Toast
                        .makeText(
                            context,
                            R.string.send_successful_a_reset_password_e_mail,
                            Toast.LENGTH_LONG
                        )
                        .show()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.change_password),
                fontSize = 16.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                letterSpacing = 0.8.sp,
                color = appColor.onBackground
            )
        }


        Spacer(modifier = Modifier.height(16.dp))
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