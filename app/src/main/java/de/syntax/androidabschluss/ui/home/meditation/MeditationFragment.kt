package de.syntax.androidabschluss.ui.home.meditation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentMeditationBinding


class MeditationFragment : Fragment() {
    private lateinit var binding: FragmentMeditationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMeditationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBreath.setOnClickListener {
            findNavController().navigate(MeditationFragmentDirections.actionMeditationFragmentToBreathFragment())
        }
        binding.imgJacobsen.setOnClickListener {
            findNavController().navigate(MeditationFragmentDirections.actionMeditationFragmentToMindfullFragment())
        }
        binding.imgRelax.setOnClickListener {
            findNavController().navigate(MeditationFragmentDirections.actionMeditationFragmentToRelaxFragment())
        }

        // animiertes Icon mit Glide
        val imginfo = binding.imgMeditationInfo
        Glide.with(this)
            .load(R.drawable.info)
            .into(imginfo)

        binding.imgMeditationInfo.setOnClickListener {
            findNavController().navigate(MeditationFragmentDirections.actionMeditationFragmentToMeditationHelpFragment())
        }
    }
}