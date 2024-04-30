package de.syntax.androidabschluss.ui.home.home

import android.app.NotificationManager
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentHomeBinding
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import de.syntax.androidabschluss.data.model.Notes
import de.syntax.androidabschluss.ui.authentication.viewmodel.FireBaseViewModel
import de.syntax.androidabschluss.viewmodel.CozyCareViewModel
import java.text.SimpleDateFormat
import java.util.Date


class HomeFragment : Fragment() {
    private val viewModel: FireBaseViewModel by activityViewModels()
    private val cozyCareViewModel: CozyCareViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private var currentDate: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Zurück Taste ist in MainActivity deaktiviert um nicht in Login oder StartScreens zu gelangen
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}

        // Sobald auf ein Datum im Kalender geklickt wird
        binding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }.time
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val formattedDate = formatter.format(date)
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBottomSheetFragment(formattedDate))
        }

        binding.fab.setOnClickListener {
            // Anzeigen des unten erstellten AlertDialogs sobald auf Float Action Button gedrückt wird (id: fab)
            showNoteDialog()
        }
        // animiertes Icon mit Glide (info Icon)
        val imgInfo = binding.imgInfo
        Glide.with(this)
            .load(R.drawable.info)
            .into(imgInfo)

        binding.imgInfo.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGettingHelpFragment())
        }

        /* Solang keine Termine im Kalendar existieren, ist das KalendarIcon statisch, wenn jedoch Termine
        vorhanden sind, wird es durch das animierte Icon ersetzt.
         */
        cozyCareViewModel.eventsList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                // animiertes Icon mit Glide (Calendar Icon)
                val imageView = binding.imgAllEvents
                Glide.with(this)
                    .load(R.drawable.calendar_anim)
                    .into(imageView)
            } else {
                binding.imgAllEvents.setImageResource(R.drawable.calendar_static)
            }
        }

        binding.imgAllEvents.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAllEventsFragment())
        }


    }

    // Alert Dialog für Schnell Notiz (wird im Firestore gespeichert)
    private fun showNoteDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val radius = 40f // Abrundung der Ecken
        val shapeDrawable = ShapeDrawable()
        // Erstellung des ShapeDrawables was abgerundete Ecken ermöglicht
        shapeDrawable.shape = RoundRectShape(
            floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius), null, null
        )
        shapeDrawable.paint.color = Color.WHITE
        // Erstellung des Titels der Note und Feld für Hinweis der Eingabe
        dialogBuilder.setTitle(R.string.fast_note)
        dialogBuilder.setMessage(R.string.write_text)
        // Eingabefeld für die Note
        val input = EditText(requireContext())
        // Datums Formatierung
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        dialogBuilder.setView(input)
        // Setzen der View und Erstellung des Speichern Buttons bzw füllen der Variablen der datenklasse
        dialogBuilder.setPositiveButton(R.string.alert_save) { _, _ ->
            val noteText = input.text.toString()
            val title = "Fast Note"
            val dateTime = currentDate
            val subTitle = ""
            val notes = dateTime?.let { Notes(id = "", title, subTitle, it, noteText, color = ContextCompat.getColor(requireContext(), R.color.defaultNote)) }
            if (notes != null) {
                viewModel.addNote(notes) // Note wird erstellt
            }
        }// Erstellung des Abbrechen Buttons
        dialogBuilder.setNegativeButton(R.string.alert_cancel) { dialog, _ ->
            dialog.dismiss() // Fenster wird nach Abbrechen direkt gelöscht
        }
        // Erstellen des AlertDialogs
        val dialog = dialogBuilder.create()
        // Sobald der AlertDialog erstellt wurde, wird als Hintergrund das shapedrawable eingesetzt (abgerundetes Fenster)
        dialog.window?.setBackgroundDrawable(shapeDrawable)
        dialog.show()
    }
}
