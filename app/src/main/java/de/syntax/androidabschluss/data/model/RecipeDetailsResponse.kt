package de.syntax.androidabschluss.data.model

data class RecipeDetailsResponse (
    val id: Int,
    val title: String,
    val image: String,
    val instructions: String,
    val servings: Int,
    val readyInMinutes: Int,
    val vegetarian: Boolean,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val extendedIngredients: List<ExtendedIngredient>
)

data class ExtendedIngredient(
    val id: Int,
    val name: String,
    val amount: Double,
    val measures: Measures,
)

data class Measures(
    val metric: Metric
)

data class Metric(
    val amount: Double,
    val unitShort: String
)