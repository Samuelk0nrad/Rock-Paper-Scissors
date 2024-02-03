package com.game.rockpaperscissors.presentation.screen.friends

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor

@Composable
fun FriendsScreen(
    navController: NavController,
    viewModel: FriendsViewModle
) {

    var getFriends by remember {
        mutableStateOf(false)
    }

    val allFriends = viewModel.allFriends.collectAsState()
    val onlineFriends = viewModel.allFriends.collectAsState()
    val RequestFriends = viewModel.allFriends.collectAsState()

    if(!getFriends){
        getFriends = true
        viewModel.getAllFriends{ _, error ->
            if(error != null){
                getFriends = false
            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(appColor.background)
    ){

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
                text = "Friends",
                fontSize = 20.sp,
                fontFamily = Oswald,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = appColor.onBackground
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ){

            item{

                Spacer(modifier = Modifier.height(34.dp))

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
                        text = "Requests",
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
//            items(allFriends.value){
//                FriendsDetails()
//
//                i++
//            }

            item{

                Spacer(modifier = Modifier.height(34.dp))

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
                        text = "Online",
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


            items(allFriends.value) {
                FriendsDetails(it.username ?:"", 0, it.profilePictureUrl)
            }


            item{

                Spacer(modifier = Modifier.height(34.dp))

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
                        text = "All",
                        fontSize = 16.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Light,
                        color = appColor.onSecondaryContainer,
                        letterSpacing = 1.28.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }

//
//            items(3) {
//                FriendsDetails()
//            }
        }

        if(!getFriends){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor.background),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}


@Composable
fun FriendsDetails(
    userName: String,
    level: Int,
    pictureUri: String?
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

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Level: $level",
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
                        .background(appColor.secondaryContainer),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Add",
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






