package de.syntax.androidabschluss.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax.androidabschluss.data.HealthyFoodRepository
import de.syntax.androidabschluss.data.remote.HealthyFoodApi
import kotlinx.coroutines.launch

class HealthyFoodViewModel : ViewModel() {

    private val healthyRepository = HealthyFoodRepository(HealthyFoodApi)

    val recipes = healthyRepository.recipes

    val recipeDetails = healthyRepository.recipeDetails

    val recipeIngredients = healthyRepository.ingredients


    // Funktion zum Abrufen von Rezepten
    fun getRecipes(query: String) {
        viewModelScope.launch {
            try {
                healthyRepository.getRecipes(query)
                Log.e("HealthyFoodVM", "getting Recipes: ${recipes.value}")
            } catch (e: Exception) {
                Log.e("HealthyFoodVM", "Error getting Recipes: $e")
            }
        }
    }
    // Funktion zum Abrufen von Rezeot Details
    fun getRecipeDetails (recipeId: Int) {
        viewModelScope.launch {
            try {
                healthyRepository.getRecipeDetails(recipeId)
                recipeDetails.value?.let {
                    getIngredients(it.id)
                }
                Log.e("HealthyFoodVM", "getting Recipe Details: ${recipeDetails.value}")
            } catch (e: Exception) {
                Log.e("HealthyFoodVM", "Error getting Recipe Details: $e")
            }
        }
    }
    // Funktion zum Abrufen von Zutaten, wird an getRecipeDetails weitergegeben. Daher private Funktion, da sie nur hier genutzt wird
    private fun getIngredients (recipeId: Int) {
        viewModelScope.launch {
            try {
                healthyRepository.getRecipeIngredients(recipeId)
                Log.e("HealthyFoodVM", "getting Recipe Details: ${recipeIngredients.value}")
            } catch (e: Exception) {
                Log.e("HealthyFoodVM", "Error getting Recipe Ingredients: $e")
            }
        }
    }

}