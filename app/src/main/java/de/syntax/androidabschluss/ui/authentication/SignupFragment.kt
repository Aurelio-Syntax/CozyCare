package de.syntax.androidabschluss.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentSignupBinding
import de.syntax.androidabschluss.ui.authentication.viewmodel.FireBaseViewModel


class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val minPasswordLength = 6
    private val viewModel: FireBaseViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupCancelButton.setOnClickListener {
            findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToStartFragment())
        }

        binding.signupSignupButton.setOnClickListener {
            val email = binding.signupMail.text.toString()
            val password = binding.signupPassword.text.toString()
            val username = binding.signupUsername.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && password.length >= minPasswordLength && username.isNotEmpty()) {
                viewModel.signup(email, password, username)
                Toast.makeText(context, R.string.signup_toast, Toast.LENGTH_SHORT).show()
            } else if (email.isNotEmpty() && password.isNotEmpty() && password.length < minPasswordLength) {
                Toast.makeText(context, R.string.passwort_length, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                findNavController().navigate(R.id.carouselActivity)
            }
        }

        val imgInfo = binding.imgPasswordInfo
        Glide.with(this)
            .load(R.drawable.info)
            .into(imgInfo)

        binding.imgPasswordInfo.setOnClickListener {
            Toast.makeText(context, R.string.password_info, Toast.LENGTH_LONG).show()
        }
    }
}