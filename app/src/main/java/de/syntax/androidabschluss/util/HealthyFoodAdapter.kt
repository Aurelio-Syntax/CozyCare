package de.syntax.androidabschluss.util

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.data.model.Recipe
import de.syntax.androidabschluss.databinding.HealthyfoodItemBinding
import de.syntax.androidabschluss.ui.home.dayPlan.HealthyFoodFragmentDirections

class HealthyFoodAdapter (
    private val dataset: List<Recipe>
): RecyclerView.Adapter<HealthyFoodAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: HealthyfoodItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = HealthyfoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        holder.binding.imgRecipe.load(item.image)
        holder.binding.tvRecipeTitle.text = item.title

        holder.binding.cvRecipe.setOnClickListener {
            val recipeId = item.id
            Log.d("HealthyFoodAdapter", "Recipe clicked: $recipeId")
            val navController = holder.binding.cvRecipe.findNavController()
            navController.navigate(HealthyFoodFragmentDirections.actionHealthyFoodFragmentToRecipeDetailsFragment(recipeId))
        }
    }

}