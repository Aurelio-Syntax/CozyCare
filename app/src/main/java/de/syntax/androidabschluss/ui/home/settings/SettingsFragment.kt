package de.syntax.androidabschluss.ui.home.settings

import android.app.AlertDialog
import android.app.LocaleManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import de.syntax.androidabschluss.BuildConfig
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.model.Notes
import de.syntax.androidabschluss.databinding.FragmentSettingsBinding
import de.syntax.androidabschluss.ui.authentication.viewmodel.FireBaseViewModel
import java.text.SimpleDateFormat
import java.util.Date

class SettingsFragment : Fragment() {
    private val viewModel: FireBaseViewModel by activityViewModels()
    private lateinit var binding: FragmentSettingsBinding
    private val SELECTED_LANGUAGE = "selected_language"

    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences("language_pref", Context.MODE_PRIVATE)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                // Benutzername des Kontos abrufen
                val username = user.displayName
                binding.tvUser.text = username
            }
        }

        val isChecked = true
        val savedLanguage = sharedPreferences.getString(SELECTED_LANGUAGE, "de") ?: ""

        // Setze die Sprache und aktualisiere die Radiobutton-Ansicht
        setAppLanguage(savedLanguage)
        updateRadioButtonState(savedLanguage)

        binding.radioEnglish.setOnClickListener {
            if (isChecked) {
                // Wird nur ausgeführt wenn die Android Version 12 oder drüber ist
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requireContext().getSystemService(LocaleManager::class.java)
                        .applicationLocales = LocaleList.forLanguageTags("en")
                    // Dafür zuständigt, dass wenn die Sprache ausgewählt wurde auch der RadioButton an dieser Stelle bleibt
                    sharedPreferences.edit().putString(SELECTED_LANGUAGE, "en").apply()
                    setAppLanguage("en")
                    updateRadioButtonState("en")
                    Toast.makeText(requireContext(), "Language has changed", Toast.LENGTH_SHORT)
                        .show()
                }
                // Wird nur ausgeführt wenn die Android Version 11 oder niedriger ist
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(
                            "en"
                        )
                    )
                    // Dafür zuständigt, dass wenn die Sprache ausgewählt wurde auch der RadioButton an dieser Stelle bleibt
                    sharedPreferences.edit().putString(SELECTED_LANGUAGE, "en").apply()
                    setAppLanguage("en")
                    updateRadioButtonState("en")
                    Toast.makeText(requireContext(), "Language has changed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }



        binding.radioGerman.setOnClickListener {
            if (isChecked) {
                // Wird nur ausgeführt wenn die Android Version 12 oder drüber ist
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requireContext().getSystemService(LocaleManager::class.java)
                        .applicationLocales = LocaleList.forLanguageTags("de")
                    // Dafür zuständigt, dass wenn die Sprache ausgewählt wurde auch der RadioButton an dieser Stelle bleibt
                    sharedPreferences.edit().putString(SELECTED_LANGUAGE, "de").apply()
                    setAppLanguage("de")
                    updateRadioButtonState("de")
                    Toast.makeText(requireContext(), "Sprache geändert", Toast.LENGTH_SHORT).show()

                }
                // Wird nur ausgeführt wenn die Android Version 11 oder niedriger ist
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(
                            "de"
                        )
                    )
                    // Dafür zuständigt, dass wenn die Sprache ausgewählt wurde auch der RadioButton an dieser Stelle bleibt
                    sharedPreferences.edit().putString(SELECTED_LANGUAGE, "de").apply()
                    setAppLanguage("de")
                    updateRadioButtonState("de")
                    Toast.makeText(requireContext(), "Sprache geändert", Toast.LENGTH_SHORT).show()
                }
            }
        }


        // Logout Button mit Logout Logik aus der Viewmodel Funktion
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToStartActivity())
        }

        /* Account Löschen Button, eine Bestätigung mit Hinweis wird abgefragt. Wenn bestätigt: Account wird gelöscht und
        man wird wieder zum StartScreen geleitet
         */
        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_Acc)
                .setMessage(R.string.delete_Message)
                .setPositiveButton(R.string.delete_Positive) { _, _ ->
                    val user = FirebaseAuth.getInstance().currentUser
                    // Nutzer Token wird erneuert, da aus Sicherheitsgründen erforderlich
                    user?.reload()
                    user?.delete()?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, R.string.delete_Successful, Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToStartActivity())
                        } else {
                            Toast.makeText(context, R.string.delete_failed, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                .setNegativeButton(R.string.delete_Negative) { accDeleteDialog, _ ->
                    accDeleteDialog.dismiss()
                }
                .create()
                .show()
        }


        // Sobald auf Passwort ändern geklickt wird, word ein Bottom Sheet Fragment geöffnet wo das die Passwort Änderunge möglich wird
        binding.btnChangePw.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToPasswordFragment())
        }

        // App Info Anzeige der App Version ect
        val imgAppInfo = binding.imgAppInfo
        Glide.with(this)
            .load(R.drawable.info)
            .into(imgAppInfo)

        // App Info mit Versionsnummer
        binding.imgAppInfo.setOnClickListener {
            val versionName = BuildConfig.VERSION_NAME
            openDialogFragment(R.drawable.cozy_launcher_foreground, "App Info", "Version: $versionName", "created by: Aurelio Silvestrini", "2024", backgroundColor = R.color.CustomColor1)
        }
    }


    // funktion zum ändern des Status der Radiobuttons für die Sprache
    private fun updateRadioButtonState(language: String) {
        binding.radioEnglish.isChecked = language == "en"
        binding.radioGerman.isChecked = language == "de"
    }
    private fun setAppLanguage(language: String) {
    }

    // Funktion zum öffnen des Dialogfragments (App Info)
    private fun openDialogFragment(image: Int, title: String, text: String, text2: String, text3: String, backgroundColor: Int) {
        val dialogFragment = AppInfoFragment.newInstance(image, title, text, text2, text3, backgroundColor)
        dialogFragment.show(childFragmentManager, "CardsDialogFragment")
    }

}
