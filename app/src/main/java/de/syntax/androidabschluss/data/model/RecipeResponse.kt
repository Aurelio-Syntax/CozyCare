package de.syntax.androidabschluss.data.model

data class RecipeResponse (
    val results: List<Recipe>,
    val totalResults: Int,
    val offset: Int,
    val number: Int
)

data class Recipe (
    val id: Int,
    val title: String,
    val image: String,
    val imageType: String
)