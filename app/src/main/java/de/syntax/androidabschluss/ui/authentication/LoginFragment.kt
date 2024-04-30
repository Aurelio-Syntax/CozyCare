package de.syntax.androidabschluss.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentLoginBinding
import de.syntax.androidabschluss.ui.authentication.viewmodel.FireBaseViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: FireBaseViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginSignupButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }

        binding.backButtonLogin.setOnClickListener {

            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToStartFragment())
        }
        // Zurücksetzen des Passwortes: Nur möglich wenn Email eingetragen, ansonsten Toast mit Hinweis
        binding.tvResetPW.setOnClickListener {
            val email = binding.loginEmailEdit.text.toString()
            if (email.isNotEmpty()) {
                viewModel.resetPassword(email)
            } else {
                Toast.makeText(context, R.string.email_missed, Toast.LENGTH_SHORT).show()
            }

        }
        // Login mit Email und Password, falls Passwort und Email nicht übereinstimmt, Toast mit Hinweis
        binding.loginButton.setOnClickListener {
            val email = binding.loginEmailEdit.text.toString()
            val password = binding.loginPasswordEdit.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(context, R.string.login_toast, Toast.LENGTH_SHORT).show()
            }
        }/* Weiterleitung ans CarouselActivity wenn ein Nutzer eingeloggt ist.
        Damit bei erneutem öffnen nicht durch jedes Startfragment navigiert werden muss*/
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                findNavController().navigate(R.id.mainActivity)
            }
        }
    }
}