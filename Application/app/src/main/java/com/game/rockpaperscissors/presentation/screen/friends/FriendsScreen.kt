package com.game.rockpaperscissors.presentation.screen.friends

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor
import com.game.rockpaperscissors.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    navController: NavController,
    viewModel: FriendsViewModel
) {

    val TAG = "FriendsViewModelFirebase"

    var getFriends by remember {
        mutableStateOf(false)
    }

    var startSearchingForFriends by remember {
        mutableStateOf(false)
    }

    var startSearchingForRequests by remember {
        mutableStateOf(false)
    }

    val allFriends = viewModel.allFriends.collectAsState()
    val onlineFriends = viewModel.onlineFriends.collectAsState()
    val requestFriends = viewModel.requestsFriends.collectAsState()

    val addFriend = viewModel.addFriend.collectAsState()

    val userName = viewModel.userName.collectAsState()



    if(!startSearchingForFriends){
        startSearchingForFriends = true
        viewModel.getAllFriends{ _, error ->
            getFriends = error == null
        }
    }

    if(!startSearchingForRequests){
        startSearchingForRequests = true
        viewModel.getRequestsFriends{ _, _ ->

        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AddButton(viewModel::onClickAddFriendButton)
        },
    ) {padding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(appColor.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable {
                        navController.popBackStack()
                    }
                    .background(appColor.secondaryContainer),
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 6.dp, start = 12.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIos,
                        contentDescription = "Go Back",
                        tint = appColor.onBackground
                    )
                }
                Text(
                    text = stringResource(id = R.string.friends),
                    fontSize = 20.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = appColor.onBackground
                )
            }
            if (getFriends) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {
                    if (requestFriends.value.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(34.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(33.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(appColor.onSecondaryContainer)
                                )
                                Text(
                                    modifier = Modifier
                                        .background(appColor.background)
                                        .padding(horizontal = 12.dp),
                                    text = stringResource(id = R.string.requests),
                                    fontSize = 16.sp,
                                    fontFamily = Oswald,
                                    fontWeight = FontWeight.Light,
                                    color = appColor.onSecondaryContainer,
                                    letterSpacing = 1.28.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        var i = 0
                        Log.d(TAG, "${allFriends.value.count()}")
                        items(requestFriends.value) {
                            FriendsDetails(
                                userName = it.username ?: "",
                                level =  i,
                                pictureUri = it.profilePictureUrl ?: "",
                                buttonText = "Add",
                            ){
                                viewModel.exceptRequest(it.username ?:"")
                            }
                            if (i != allFriends.value.size) {
                                Spacer(modifier = Modifier.height(16.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ){
                                Spacer(
                                    modifier = Modifier
                                        .height(1.dp)
                                        .fillMaxWidth(0.7f)
                                        .background(appColor.onSecondaryContainer.copy(alpha = 0.5f))
                                )

                            }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            i++
                        }
                    }
                    if (onlineFriends.value.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(34.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(33.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(appColor.onSecondaryContainer)
                                )
                                Text(
                                    modifier = Modifier
                                        .background(appColor.background)
                                        .padding(horizontal = 12.dp),
                                    text = stringResource(id = R.string.online),
                                    fontSize = 16.sp,
                                    fontFamily = Oswald,
                                    fontWeight = FontWeight.Light,
                                    color = appColor.onSecondaryContainer,
                                    letterSpacing = 1.28.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        var i = 0
                        Log.d(TAG, "${allFriends.value.count()}")
                        items(onlineFriends.value) {
                            FriendsDetails(
                                userName = it.username ?: "",
                                level =  i,
                                pictureUri = it.profilePictureUrl ?: "",
                                buttonText = stringResource(id = R.string.play),
                            ){

                            }
                            if (i != 0 || i != allFriends.value.size - 1) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Spacer(
                                    modifier = Modifier
                                        .height(1.dp)
                                        .background(appColor.onSecondaryContainer)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            i++
                        }
                    }
                    if (allFriends.value.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(34.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(33.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(appColor.onSecondaryContainer)
                                )
                                Text(
                                    modifier = Modifier
                                        .background(appColor.background)
                                        .padding(horizontal = 12.dp),
                                    text = stringResource(id = R.string.all),
                                    fontSize = 16.sp,
                                    fontFamily = Oswald,
                                    fontWeight = FontWeight.Light,
                                    color = appColor.onSecondaryContainer,
                                    letterSpacing = 1.28.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                        var i = 0
                        Log.d(TAG, "${allFriends.value.count()}")
                        items(allFriends.value) {
                            FriendsDetails(
                                userName = it.username ?: "",
                                level =  i,
                                pictureUri = it.profilePictureUrl ?: "",
                                buttonText = stringResource(id = R.string.play),
                            ) {

                            }

                            if (i != allFriends.value.size - 1) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Spacer(
                                    modifier = Modifier
                                        .height(1.dp)
                                        .background(appColor.onSecondaryContainer)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            i++
                        }
                    }
                    if (requestFriends.value.isEmpty() &&
                        onlineFriends.value.isEmpty() &&
                        allFriends.value.isEmpty()) {
                        item {
                            Text(
                                text = stringResource(id = R.string.you_dont_have_friends_jet),
                                fontSize = 20.sp,
                                fontFamily = Oswald
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(appColor.background),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }

    }


    var error by remember {
        mutableStateOf(false)
    }
    var errorText by remember {
        mutableStateOf("")
    }

    if(!addFriend.value){
        errorText = ""
        error = false
    }


    if(addFriend.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    viewModel.onCancelAddFriendButton()
                }
        )

        var isWorking by remember {

            mutableStateOf(false)
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .clip(RoundedCornerShape(46.dp))
                    .background(appColor.onSecondaryContainer)
                    .padding(14.dp)
            ) {
                Row {

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = userName.value,
                        onValueChange = {
                            error = false
                            errorText = ""

                            viewModel.updateUserName(it)
                        },
                        placeholder = {
                            Text(
                                text = "Enter a username",
                                fontFamily = Oswald,
                                fontSize = 14.sp,
                                letterSpacing = 1.sp
                            )
                        },
                        trailingIcon = {
                            Icon(
                                modifier = Modifier
                                    .size(42.dp)
                                    .padding(end = 12.dp)
                                    .clickable(
                                        interactionSource = MutableInteractionSource(),
                                        indication = null
                                    ) {
                                        isWorking = true

                                        viewModel.onClickAddFriend { errorMessage ->
                                            if (errorMessage != null) {
                                                error = true
                                                errorText = errorMessage

                                            }

                                            isWorking = false
                                        }
                                    },
                                imageVector = Icons.Rounded.Add,
                                contentDescription = null
                            )
                        },
                        shape = RoundedCornerShape(32.dp),
                        textStyle = LocalTextStyle.current.copy(
                            fontFamily = Oswald
                        ),
                        maxLines = 1,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = appColor.onSecondary,
                            unfocusedBorderColor = appColor.secondaryContainer
                        ),
                        isError = error,
                    )
                }

                if(error) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp),
                        text = errorText,
                        fontSize = 12.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.ExtraLight,
                        color = appColor.red
                    )
                }


                if(isWorking) {
                    Spacer(modifier = Modifier.height(18.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
                    }
                }

            }
        }
    }
}



@Composable
fun FriendsDetails(
    userName: String,
    level: Int,
    pictureUri: String?,
    buttonText: String,
    onClick: () -> Unit,
){


    Box(modifier = Modifier
        .fillMaxWidth()
        .height(73.dp)
    ){
        Row {

            Box(
                modifier = Modifier
                    .size(73.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(appColor.onBackground)
            ){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Profile"
                )
                if (pictureUri != "") {
                    AsyncImage(
                        model = pictureUri,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(150.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.width(29.dp))


            Column{
                Text(
                    text = userName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = Oswald,
                    color = appColor.onBackground
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "${stringResource(R.string.level)}: $level",
                    fontFamily = Oswald,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    color = appColor.onBackground

                )
            }



            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ){


                Box(
                    modifier = Modifier
                        .height(42.dp)
                        .width(102.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(appColor.secondaryContainer)
                        .clickable {
                            onClick()
                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = buttonText,
                        fontFamily = Oswald,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        color = appColor.onBackground
                    )
                }
            }

        }
    }

}

@Composable
fun AddButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = appColor.onSecondaryContainer
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}



