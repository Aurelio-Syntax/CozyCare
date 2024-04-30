package de.syntax.androidabschluss.ui.home.dayPlan


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.databinding.FragmentDayPlanDetailBinding


class DayPlanDetailFragment : Fragment() {
    private lateinit var binding: FragmentDayPlanDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDayPlanDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgHealthy.setOnClickListener {
            findNavController().navigate(DayPlanDetailFragmentDirections.actionDayPlanDetailFragmentToHealthyFoodFragment())
        }
        binding.imgToDo.setOnClickListener {
            findNavController().navigate(DayPlanDetailFragmentDirections.actionDayPlanDetailFragmentToToDoFragment())
        }

        }
    }