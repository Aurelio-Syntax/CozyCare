package de.syntax.androidabschluss.ui.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.syntax.androidabschluss.databinding.FragmentPasswordBinding
import de.syntax.androidabschluss.ui.authentication.viewmodel.FireBaseViewModel


class PasswordFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPasswordBinding
    private val fireBaseViewModel: FireBaseViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fireBaseViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                // Benutzername des Kontos abfragen
                val username = user.displayName
                binding.tvUser.text = username
            }
        }

        binding.btnChangePw.setOnClickListener {
            fireBaseViewModel.currentUser.value?.let { it1 ->
                fireBaseViewModel.changePassword(
                    currentUser = it1,
                    newPassword = binding.etPassword.text.toString(),
                    confirmPassword = binding.etPasswordConfirm.text.toString()
                )
                findNavController().navigate(PasswordFragmentDirections.actionPasswordFragmentToSettingsFragment())
            }


        }
    }
}