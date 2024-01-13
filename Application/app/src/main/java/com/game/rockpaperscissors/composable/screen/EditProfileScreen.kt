package com.game.rockpaperscissors.composable.screen

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.game.rockpaperscissors.data.PlayerDataState
import com.game.rockpaperscissors.data.local.database.PlayerDataEvent
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor
import java.io.File


@Composable
fun EditProfileScreen(
    state: PlayerDataState,
    onEvent: (PlayerDataEvent) -> Unit,
    nevController: NavController,
    context: Context

) {
    var fullName by remember {
        mutableStateOf(state.allPlayer[0].name)
    }

    var userName by remember {
        mutableStateOf(state.allPlayer[0].userName)
    }

    var birthDate by remember {
        mutableStateOf(state.allPlayer[0].birthData)
    }

    var gender by remember {
        mutableStateOf(state.allPlayer[0].gender)
    }

    var showName by remember {
        mutableStateOf(state.allPlayer[0].showName)
    }

    var showData by remember {
        mutableStateOf(state.allPlayer[0].showData)
    }

    var fileName: String? by remember {
        mutableStateOf(null)

    }

    onEvent(PlayerDataEvent.SetFullName(fullName))
    onEvent(PlayerDataEvent.SetUserName(userName))
    onEvent(PlayerDataEvent.SetBirthData(birthDate))
    onEvent(PlayerDataEvent.SetGender(gender))
    onEvent(PlayerDataEvent.SetShowData(showData))
    onEvent(PlayerDataEvent.SetShowName(showName))
    if(state.userImage.isBlank()){
        onEvent(PlayerDataEvent.SetUserImage(fileName!!))

    }






    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri ->
            if(uri != null){
                fileName = saveImageToInternalStorage(context = context, uri = uri, fileName = "profile_picture")

                onEvent(PlayerDataEvent.SetUserImage(fileName!!))

            }
        }
    )




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

                            nevController.popBackStack()
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
                        text = "Your Profile",
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

                                onEvent(PlayerDataEvent.CreateNewPlayer)
                                nevController.popBackStack()

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


                val imageFile: File? = getImage(context, fileName)


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
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {


                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .clickable {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
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
            value = /*fullName*/ state.fullName,
            onValueChange = {
                fullName = it
                onEvent(PlayerDataEvent.SetFullName(it))
            },
            placeholder = "Full Name"
        )


        Spacer(modifier = Modifier.height(53.dp))

        CustomTextFiled(
            value = /*userName*/ state.userName,
            onValueChange = {
                userName = it
                onEvent(PlayerDataEvent.SetUserName(it))
            },
            placeholder = "User Name"
        )

        Spacer(modifier = Modifier.height(53.dp))


        CustomTextFiled(
            value = /*birthDate*/ state.birthData,
            onValueChange = {
                birthDate = it
                onEvent(PlayerDataEvent.SetBirthData(it))
            },
            placeholder = "Birth Date",
            modifier = Modifier.width(269.dp),
            onlyNumbers = true
        )

        Spacer(modifier = Modifier.height(63.dp))


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 51.dp)
        ){


            Text(
                modifier = Modifier
                    .width(120.dp),
                fontFamily = Oswald,
                text = "Gender",
                fontWeight = FontWeight.ExtraLight,
                fontSize = 20.sp,
                letterSpacing = 1.sp,
                color = appColor.onBackground
            )

            Spacer(modifier = Modifier.width(8.dp))


            Spinner(
                itemList = listOf("---","MALE", "FEMININE", "DIVERS"),
                onItemSelected = {
                    gender = it

                    onEvent(PlayerDataEvent.SetGender(it))

                },
                value = gender,

            )
        }

        Spacer(modifier = Modifier.height(64.dp))



        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 51.dp, end = 51.dp)
                .clickable {
                    showName = !showName
                    onEvent(PlayerDataEvent.SetShowData(showName))
                },
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = if(showName) Icons.Rounded.CheckBoxOutlineBlank else Icons.Rounded.CheckBox,
                contentDescription = "CheckBox",
                tint = appColor.onBackground
            )

            Spacer(modifier = Modifier.width(13.dp))

            Text(
                modifier = Modifier.height(24.dp),
                text = "Don’t show the full name",
                fontSize = 15.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.75.sp,
                color = appColor.onBackground,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 51.dp, end = 51.dp)
                .clickable {
                    showData = !showData
                    onEvent(PlayerDataEvent.SetShowData(showData))
                },
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = if(showData) Icons.Rounded.CheckBoxOutlineBlank else Icons.Rounded.CheckBox,
                contentDescription = "CheckBox",
                tint = appColor.onBackground
            )


            Spacer(modifier = Modifier.width(13.dp))

            Text(
                modifier = Modifier.height(24.dp),
                text = "Don’t show the full birth date",
                fontSize = 15.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.75.sp,
                color = appColor.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}