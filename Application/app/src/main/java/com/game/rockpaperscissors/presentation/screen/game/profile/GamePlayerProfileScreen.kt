package com.game.rockpaperscissors.presentation.screen.game.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.game.rockpaperscissors.data.local.database.PlayerData
import com.game.rockpaperscissors.presentation.screen.game.GameViewModel
import com.game.rockpaperscissors.presentation.screen.getImage
import com.game.rockpaperscissors.presentation.screen.profile.ProfileNames
import com.game.rockpaperscissors.presentation.screen.profile.ProfileViewModel
import com.game.rockpaperscissors.presentation.screen.profile.hideDate
import com.game.rockpaperscissors.presentation.screen.profile.hideName
import com.game.rockpaperscissors.ui.theme.Oswald
import com.game.rockpaperscissors.ui.theme.appColor
import java.io.File

@Composable
fun GamePlayerProfileScreen(
    navController: NavController,
    viewModel: GameViewModel,
) {

    val userName = viewModel.selectedPlayer?.username ?: ""
    val profilePicture = viewModel.selectedPlayer?.profilePictureUrl ?: ""


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor.background)
    ) {
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
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        },
                        imageVector = Icons.Rounded.ArrowBackIos,
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
                        text = "$userName Profile",
                        fontSize = 20.sp,
                        fontFamily = Oswald,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = Color.White
                    )
                }

            }


            Box(
                modifier = Modifier
                    .padding(top = 46.dp)
                    .height(86.dp)
                    .width(86.dp)
                    .clip(RoundedCornerShape(100.dp))

            ) {
//                val fileName = player.userImage

//                val imageFile: File? = getImage(context, fileName)

                Image(
                    contentDescription = "Profile",
                    imageVector = Icons.Rounded.Person,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(appColor.onBackground)
                )


//                if (imageFile != null) {
//                    AsyncImage(
//                        model = imageFile.toUri(),
//                        contentDescription = null,
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = ContentScale.Crop
//                    )
//                }
            }
        }


        LazyColumn{

            item {
                Spacer(modifier = Modifier.height(28.dp))

                ProfileNames(name = userName, title = "Full Name")
            }

            item {
                Spacer(modifier = Modifier.height(36.dp))

                ProfileNames(name = userName, title = "User Name")
            }
        }
    }
}

















