package de.syntax.androidabschluss.ui.home.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.syntax.androidabschluss.databinding.FragmentGettingHelpBinding


class GettingHelpFragment : Fragment() {
    private lateinit var binding: FragmentGettingHelpBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGettingHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

}