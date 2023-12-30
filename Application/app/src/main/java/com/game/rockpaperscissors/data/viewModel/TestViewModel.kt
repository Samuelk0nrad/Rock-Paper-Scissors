package com.game.rockpaperscissors.data.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    val string: String
): ViewModel() {
    fun print(){
        Log.d("Room.databaseBuilder.PlayerDatabase","test: $string")
    }
}