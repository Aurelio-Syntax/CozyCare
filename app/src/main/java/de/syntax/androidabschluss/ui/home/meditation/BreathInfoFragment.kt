package de.syntax.androidabschluss.ui.home.meditation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.syntax.androidabschluss.databinding.FragmentBreathInfoBinding


class BreathInfoFragment : Fragment() {
    private lateinit var binding: FragmentBreathInfoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBreathInfoBinding.inflate(inflater, container, false)
        return binding.root
    }


}