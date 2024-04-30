package de.syntax.androidabschluss.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax.androidabschluss.data.model.RecipeDetailsResponse
import de.syntax.androidabschluss.data.model.RecipeResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL_1 = "https://api.spoonacular.com/"

private val interceptor = object : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Content-Language", "de")
            .build()
        return chain.proceed(newRequest)
    }
}

private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val httpClient = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .addInterceptor(logger)
    .build()

// Kommunikation - übersetzung der Antwort
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_1)
    .client(httpClient)
    .build()

interface HealthyFoodApiService {

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String,
        @Query("excludeIngredients") excludeIngredients: String? = null,
        @Query("number") number: Int
    ): RecipeResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): RecipeDetailsResponse
}

object HealthyFoodApi {
    val retrofitServiceHealthy: HealthyFoodApiService by lazy { retrofit.create(HealthyFoodApiService::class.java) }
}