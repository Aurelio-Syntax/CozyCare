package de.syntax.androidabschluss.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.syntax.androidabschluss.BuildConfig
import de.syntax.androidabschluss.data.model.ExtendedIngredient
import de.syntax.androidabschluss.data.model.Recipe
import de.syntax.androidabschluss.data.model.RecipeDetailsResponse
import de.syntax.androidabschluss.data.remote.HealthyFoodApi

class HealthyFoodRepository (private val api: HealthyFoodApi) {

    private val key = BuildConfig.apiKeyRecipe


    private val _recipes = MutableLiveData<List<Recipe>>()

    val recipes: LiveData<List<Recipe>>
        get() = _recipes

    private val _recipeDetails = MutableLiveData<RecipeDetailsResponse>()

    val recipeDetails: LiveData<RecipeDetailsResponse>
        get() = _recipeDetails


    private val _ingredients = MutableLiveData<List<ExtendedIngredient>>()

    val ingredients: LiveData<List<ExtendedIngredient>>
        get() = _ingredients



    suspend fun getRecipes (query: String) {
        try {
            _recipes.postValue(api.retrofitServiceHealthy.searchRecipes(
                query = query,
                apiKey = key,
                excludeIngredients = null,
                number = 10
            ).results)
            Log.e("HealthyFoodRepo", "searchRecipes")
        } catch (e: Exception) {
            Log.e("HealthyFoodRepo", "Error getting Recipes: $e")
        }
    }

    suspend fun getRecipeDetails (recipeId: Int) {
        try {
            _recipeDetails.postValue(api.retrofitServiceHealthy.getRecipeDetails(
                id = recipeId,
                apiKey = key
            ))
            Log.e("HealthyFoodRepo", "getRecipeDetails")
        } catch (e: Exception) {
            Log.e("HealthyFoodRepo", "Error getting Recipe Details: $e")
        }
    }

    suspend fun getRecipeIngredients (recipeId: Int) {
        try {
            _ingredients.postValue(api.retrofitServiceHealthy.getRecipeDetails(
                id = recipeId,
                apiKey = key
            ).extendedIngredients)
            Log.e("HealthyFoodRepo", "getRecipeIngredients")
        } catch (e: Exception) {
            Log.e("HealthyFoodRepo", "Error getting Ingredients: $e")
        }
    }
}