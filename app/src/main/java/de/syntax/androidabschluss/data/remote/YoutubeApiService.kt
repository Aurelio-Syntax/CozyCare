package de.syntax.androidabschluss.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax.androidabschluss.data.model.VideoListResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

// Kommunikation - übersetzung der Antwort
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(httpClient)
    .build()

interface YoutubeApiService {

    @GET("/youtube/v3/search")
    suspend fun getVideos(
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("maxResults") maxResults: Int,
        @Query("key") key: String,
        @Query("q") searchQuery: String
    ) : VideoListResponse

}

object YoutubeApi {
    val retrofitService: YoutubeApiService by lazy { retrofit.create(YoutubeApiService::class.java) }
}