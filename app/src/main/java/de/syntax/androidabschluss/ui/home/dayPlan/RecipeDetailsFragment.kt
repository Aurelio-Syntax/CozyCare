package de.syntax.androidabschluss.ui.home.dayPlan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import de.syntax.androidabschluss.databinding.FragmentRecipeDetailsBinding
import de.syntax.androidabschluss.util.HealthyFoodDetailsAdapter
import de.syntax.androidabschluss.viewmodel.HealthyFoodViewModel


class RecipeDetailsFragment : Fragment() {
    private lateinit var binding: FragmentRecipeDetailsBinding
    private val viewModel: HealthyFoodViewModel by activityViewModels()

    private var recipeId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Übergabe aus dem HealthyFoodFragment
        arguments?.let {
            recipeId = it.getInt("recipeId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeId?.let { viewModel.getRecipeDetails(it) }
        Log.d("RecipeDetailsFragment", "Recipe clicked: $recipeId")

        /* überwachung der "recipeDetails", sucht die passenden Details des geklickten
        Rezepts herraus und bindet die Informationen an die jeweiligen TextViews
         */
        viewModel.recipeDetails.observe(viewLifecycleOwner) {
            binding.tvReadyInMinutes.text = it.readyInMinutes.toString()
            binding.imgRecipeDetail.load(it.image)
            binding.tvRecipeInstructions.text = it.instructions
            binding.rvRecipeName.text = it.title
            binding.tvServings.text = it.servings.toString()
            binding.tvVegan.text = it.vegan.toString()
            binding.tvVegetarian.text = it.vegetarian.toString()
            binding.tvGlutenfree.text = it.glutenFree.toString()
        }
        /* Überwachung der "recipeIngredients", sucht die passenden Zutaten des geklickten
        Rezepts herraus, übergibt diese dem Adapter um es an die passenden Textviews zu binden
        und in der RecyclerView anzeigen zu lassen
         */
        recipeId?.let { viewModel.getRecipeDetails(it) }
        viewModel.recipeIngredients.observe(viewLifecycleOwner) {
            Log.e("RecipeDetailsFragment", "getting ingredients $it")
            binding.rvRecpieDetails.adapter = HealthyFoodDetailsAdapter(it)
        }




    }


}