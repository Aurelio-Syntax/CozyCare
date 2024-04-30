package de.syntax.androidabschluss.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.syntax.androidabschluss.BuildConfig
import de.syntax.androidabschluss.data.model.Item
import de.syntax.androidabschluss.data.remote.YoutubeApi

class YoutubeRepository (private val api: YoutubeApi) {

    private val key = BuildConfig.apiKeyYoutube

    private val _videos = MutableLiveData<List<Item>>()
    val videos: LiveData<List<Item>>
        get() = _videos

    suspend fun getVideos () {
        try {
            _videos.postValue(api.retrofitService.getVideos(
                part = "snippet",
                chart = "mostPopular",
                maxResults = 5,
                key = key,
                searchQuery = "NatureSoundsNoCopyright"
            ).items)
            Log.e("YoutubeRepo", "getVideos")
        } catch (e: Exception) {
            Log.e("YoutubeRepo", "Error getting Videos: $e")
        }
    }
}