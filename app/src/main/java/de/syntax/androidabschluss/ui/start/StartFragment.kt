package de.syntax.androidabschluss.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToLoginFragment())
        }

        binding.btnSignup.setOnClickListener {
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToSignupFragment())
        }
    }

}