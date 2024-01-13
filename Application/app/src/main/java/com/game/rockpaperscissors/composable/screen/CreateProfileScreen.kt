package com.game.rockpaperscissors.composable.screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.game.rockpaperscissors.data.PlayerDataState
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.local.database.PlayerDataEvent
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


@Composable
fun CreateProfileScreen(
    state: PlayerDataState,
    onEvent: (PlayerDataEvent) -> Unit,
    nevController: NavController,
    context: Context
) {


    var fullName by remember {
        mutableStateOf("")
    }

    var userName by remember {
        mutableStateOf("")
    }

    var birthDate by remember {
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

                onEvent(PlayerDataEvent.SetUserImage(fileName!!))

            }
        }
    )

    var isBlank by remember {
        mutableStateOf(false)
    }

    var isNameBlank by remember {
        mutableStateOf(false)
    }
    var isUserNameBlank by remember {
        mutableStateOf(false)
    }

    if(isBlank){
        isNameBlank = fullName == ""
        isUserNameBlank = userName == ""
    }


    var showName by remember {
        mutableStateOf(true)
    }

    var showDate by remember {
        mutableStateOf(true)
    }

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(appColor.background)
    ){

        item {
            Spacer(modifier = Modifier.height(111.dp))

            Text(
                modifier = Modifier.padding(start = 31.dp),
                text = "Create New Account",
                fontSize = 32.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Normal,
                color = appColor.onBackground
            )

            Spacer(modifier = Modifier.height(30.dp))
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
        }

        item {

            Spacer(modifier = Modifier.height(43.dp))

            CustomTextFiled(
                value = /*fullName*/ state.fullName,
                onValueChange = {
                    fullName = it
                    onEvent(PlayerDataEvent.SetFullName(it))
                },
                placeholder = "Full Name",
                lineColor = if(isNameBlank) appColor.red else appColor.onBackground
            )
        }

        item {


            Spacer(modifier = Modifier.height(43.dp))

            CustomTextFiled(
                value = /*userName*/ state.userName,
                onValueChange = {
                    userName = it
                    onEvent(PlayerDataEvent.SetUserName(it))
                },
                placeholder = "User Name",
                lineColor = if(isUserNameBlank) appColor.red else appColor.onBackground
            )

            Spacer(modifier = Modifier.height(43.dp))
        }

        item {
            CustomTextFiled(
                value = /*birthDate*/ state.birthData,
                onValueChange = {
                    birthDate = it
                    onEvent(PlayerDataEvent.SetBirthData(it))
                },
                placeholder = "Birth Date",
                modifier = Modifier.width(269.dp),
                onlyNumbers = true,
                lineColor = appColor.onBackground
            )

            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 51.dp)
            ) {

                var gender by remember {
                    mutableStateOf("---")
                }
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
                    itemList = listOf("---", "MALE", "FEMININE", "DIVERS"),
                    onItemSelected = {
                        gender = it

                        onEvent(PlayerDataEvent.SetGender(it))

                    },
                    value = gender
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }

        item {



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 51.dp, end = 51.dp)
                    .clickable {
                        showName = !showName
                        onEvent(PlayerDataEvent.SetShowData(showName))
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = if (showName) Icons.Rounded.CheckBoxOutlineBlank else Icons.Rounded.CheckBox,
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
        }

        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 51.dp, end = 51.dp)
                    .clickable {
                        showDate = !showDate
                        onEvent(PlayerDataEvent.SetShowData(showDate))
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = if (showDate) Icons.Rounded.CheckBoxOutlineBlank else Icons.Rounded.CheckBox,
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

        item {
            Spacer(modifier = Modifier.height(30.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, bottom = 64.dp, end = 24.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(appColor.secondaryContainer)
                        .fillMaxWidth()
                        .clickable {
                            if (state.fullName.isNotBlank() && state.userName.isNotBlank()) {
                                nevController.navigate(Screen.HomeScreen.route)
                            } else {
                                isBlank = true
                            }
                            onEvent(PlayerDataEvent.CreateNewPlayer)

                        }
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Create New Account",
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = appColor.onBackground
                    )
                }
            }
        }

        item{
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}



@Composable
fun CustomTextFiled(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    onlyNumbers: Boolean = false,
    lineColor: Color = appColor.onBackground
) {
    Box (
        modifier = modifier
            .padding(start = 51.dp, end = 51.dp)
            .height(35.dp),
        contentAlignment = Alignment.Center
    ){

        BasicTextField(
            modifier = Modifier.fillMaxSize(),
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontFamily = Oswald,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                letterSpacing = 1.sp,
                color = appColor.onBackground
            ),
            singleLine = true,
            keyboardOptions = if(!onlyNumbers) KeyboardOptions.Default
                else KeyboardOptions(keyboardType = KeyboardType.Number),
            cursorBrush = SolidColor(appColor.onBackground)
        )

        if (value == "") {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                fontFamily = Oswald,
                text = placeholder,
                fontWeight = FontWeight.ExtraLight,
                fontSize = 20.sp,
                letterSpacing = 1.sp,
                color = appColor.onBackground
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(lineColor)
            )
        }

    }

}

@Composable
fun Spinner(
    itemList: List<String>,
    onItemSelected: (selectedItem: String) -> Unit,
    value: String
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .width(114.dp)
            .height(30.dp)
            .clickable {
                expanded = !expanded
            },
        contentAlignment = Alignment.CenterEnd
    ){
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = value,
            fontSize = 20.sp,
            fontFamily = Oswald,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = appColor.onBackground
        )


        Icon(
            imageVector = if(!expanded) Icons.Rounded.KeyboardArrowDown else Icons.Rounded.KeyboardArrowUp,
            contentDescription = "Arrow Down",
            tint = appColor.onBackground
        )



        DropdownMenu(
            modifier = Modifier.background(appColor.onSecondaryContainer),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            itemList.forEach{
                DropdownMenuItem(text = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        fontSize = 20.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = appColor.onBackground
                    )
                }, onClick = {
                    expanded = false
                    onItemSelected(it)
                })
            }
        }
    }
}



fun saveImageToInternalStorage(context: Context, uri: Uri, fileName: String): String {
    try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File(context.filesDir, "image_$fileName.png")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return "image_$fileName.png"
}

fun getImage(context: Context, filename: String?): File? {

    return if(filename != null) {
        val filesDir = context.filesDir
        val imageFile = File(filesDir, filename)

        // Check if the file exists
        if (imageFile.exists()) {
            imageFile
        } else {
            null
        }
    } else{
        null
    }
}


