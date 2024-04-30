package de.syntax.androidabschluss.ui.home.dayPlan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import de.syntax.androidabschluss.databinding.FragmentHealthyFoodBinding
import de.syntax.androidabschluss.util.HealthyFoodAdapter
import de.syntax.androidabschluss.viewmodel.HealthyFoodViewModel


class HealthyFoodFragment : Fragment() {
    private lateinit var binding: FragmentHealthyFoodBinding
    private val viewModel: HealthyFoodViewModel by activityViewModels()
    private val query: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHealthyFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Es werden Anfangs Reepte ohne explizit ausgeführte Suche angezeigt
        viewModel.getRecipes(query)

        // Abruf der Rezepte mit Suchlogik, erst nachdem der Nutzer seine Auswahl eingetippt und bestätigt hat
        viewModel.recipes.observe(viewLifecycleOwner) {
            Log.e("Fragment", viewModel.recipes.toString())
            binding.rvRecipe.adapter = HealthyFoodAdapter(it)
            binding.svRecipe.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        viewModel.getRecipes(query)
                        return true
                    } else {
                        return false
                    }

                }
                // Logik falls "true" sobald der nutzer einen Buchstaben eintippt wird jedes Mal die Liste aktualisiert (Zwecks Api Kontigent erstmal abgeschaltet)
                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }
    }
}


