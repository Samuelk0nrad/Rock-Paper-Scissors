package com.thephoenix.rockpaperscissors.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

@Composable
fun testScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        var requestId by remember { mutableStateOf(0) }
        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data("https://cataas.com/cat")
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .setParameter("requestId", requestId, memoryCacheKey = null)
            .build()

        AsyncImage(
            model = imageRequest,
            contentDescription = "",
            modifier = Modifier.clickable(onClick = {
                requestId++
            })
        )
    }
}









