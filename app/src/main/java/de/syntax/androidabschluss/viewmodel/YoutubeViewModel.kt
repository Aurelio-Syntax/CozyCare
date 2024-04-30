package de.syntax.androidabschluss.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax.androidabschluss.data.YoutubeRepository
import de.syntax.androidabschluss.data.remote.YoutubeApi
import kotlinx.coroutines.launch

class YoutubeViewModel : ViewModel() {

    private val repository = YoutubeRepository(YoutubeApi)

    val videos = repository.videos

    fun getVideos() {
        viewModelScope.launch {
            try {
                repository.getVideos()
                Log.e("YoutubeVM", "getting Videos: ${videos.value}")
            } catch (e: Exception) {
                Log.e("YoutubeVM", "Error getting Videos: $e")
            }

        }
    }
}