package com.game.rockpaperscissors.composable.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.composable.Player
import com.game.rockpaperscissors.data.PlayerDataState
import com.game.rockpaperscissors.data.Screen
import com.game.rockpaperscissors.data.ViewModel.GameViewModel
import com.game.rockpaperscissors.data.ViewModel.PlayerViewModel
import com.game.rockpaperscissors.data.local.database.PlayerDataEvent
import com.game.rockpaperscissors.ui.theme.Oswald


@Composable
fun CreateProfileScreen(
    state: PlayerDataState,
    onEvent: (PlayerDataEvent) -> Unit,
    nevController: NavController

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




    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        Spacer(modifier = Modifier.height(111.dp))

        Text(
            modifier = Modifier.padding(start = 31.dp),
            text = "Create New Account",
            fontSize = 32.sp,
            fontFamily = Oswald,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(30.dp))



        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(90.dp))
                    .background(MaterialTheme.colorScheme.onBackground),
                contentAlignment = Alignment.Center

            ){
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(13.dp),
                    imageVector = Icons.Rounded.CameraAlt,
                    contentDescription = "Camara",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }

        Spacer(modifier = Modifier.height(43.dp))

        CustomTextFiled(
            value = /*fullName*/ state.fullName,
            onValueChange = {
                fullName = it
                onEvent(PlayerDataEvent.SetFullName(it))
            },
            placeholder = "Full Name"
        )


        Spacer(modifier = Modifier.height(43.dp))

        CustomTextFiled(
            value = /*userName*/ state.userName,
            onValueChange = {
                userName = it
                onEvent(PlayerDataEvent.SetUserName(it))
            },
            placeholder = "User Name"
        )

        Spacer(modifier = Modifier.height(43.dp))


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

        Spacer(modifier = Modifier.height(30.dp))


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 51.dp)
        ){

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
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(8.dp))


            Spinner(
                itemList = listOf("---","MALE", "FEMININE", "DIVERS"),
                onItemSelected = {
                    gender = it

                    onEvent(PlayerDataEvent.SetGender(it))

                },
                value = gender
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        var showName by remember {
            mutableStateOf(true)
        }

        var showDate by remember {
            mutableStateOf(true)
        }

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
                tint = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(13.dp))

            Text(
                modifier = Modifier.height(24.dp),
                text = "Don’t show the full name",
                fontSize = 15.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.75.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 51.dp, end = 51.dp)
                .clickable {
                    showDate = !showDate
                    onEvent(PlayerDataEvent.SetShowData(showDate))
                },
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = if(showDate) Icons.Rounded.CheckBoxOutlineBlank else Icons.Rounded.CheckBox,
                contentDescription = "CheckBox",
                tint = MaterialTheme.colorScheme.onBackground
            )


            Spacer(modifier = Modifier.width(13.dp))

            Text(
                modifier = Modifier.height(24.dp),
                text = "Don’t show the full birth date",
                fontSize = 15.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.75.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, bottom = 64.dp, end = 24.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .fillMaxWidth()
                    .clickable {
                        onEvent(PlayerDataEvent.CreateNewPlayer)
                        nevController.navigate(Screen.HomeScreen.route)
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
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}


@Composable
fun CustomTextFiled(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    onlyNumbers: Boolean = false

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
                color = MaterialTheme.colorScheme.onBackground
            ),
            singleLine = true,
            keyboardOptions = if(!onlyNumbers) KeyboardOptions.Default
                else KeyboardOptions(keyboardType = KeyboardType.Number)
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
                color = MaterialTheme.colorScheme.onBackground
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
                    .background(MaterialTheme.colorScheme.onBackground)
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
            color = MaterialTheme.colorScheme.onBackground
        )


        Icon(
            imageVector = Icons.Rounded.KeyboardArrowDown,
            contentDescription = "Arrow Down"
        )



        DropdownMenu(
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
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }, onClick = {
                    expanded = false
                    onItemSelected(it)
                })
            }
        }
    }
}



