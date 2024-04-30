package de.syntax.androidabschluss.ui.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.model.Events
import de.syntax.androidabschluss.databinding.BottomsheetFragmentBinding
import de.syntax.androidabschluss.util.CalendarAdapter
import de.syntax.androidabschluss.viewmodel.CozyCareViewModel

class BottomSheetFragment: BottomSheetDialogFragment() {
    private lateinit var binding: BottomsheetFragmentBinding
    private val cozyCareViewModel: CozyCareViewModel by activityViewModels()

    private var formattedDate: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Übergabe aus dem Homefragment sobald auf ein bestimmten Tag geklickt wird,
        wird der Tag dem BottomSheetFragment übergeben um einen Termin an diesem Tag
        erstellen zu können.
         */
        arguments?.let {
            formattedDate = it.getString("formattedDate").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDate.text = formattedDate

        // Speichert einen neuen Termin im events_table
        binding.btnSave.setOnClickListener {
            val hour = binding.timepicker.hour
            val minute = binding.timepicker.minute
            val time = String.format("%02d:%02d", hour, minute)

            val event = Events(
                id = 0,
                name = binding.etTitle.text.toString(),
                date = formattedDate,
                time = time, // Default time, can be adjusted
                description = binding.etDescription.text.toString()
            )
            if (binding.etTitle.text.isNullOrEmpty()) {
                Toast.makeText(context, R.string.description_required, Toast.LENGTH_SHORT).show()
            } else {
                cozyCareViewModel.insertEvent(event)
                findNavController().navigate(BottomSheetFragmentDirections.actionBottomSheetFragmentToHomeFragment())
            }
        }

        /* Die Textviews, Timepicker ect werden erst im BottomSheetFragment angezeigt
        wenn auf den Button "etTitle" geklickt wird. Jenachdem welcher Zustand zutrifft ändert
        sich der Text innerhalb des Buttons
         */
        binding.btnAddAppointment.setOnClickListener {
            binding.etTitle.isVisible = !binding.etTitle.isVisible
            binding.tvDate.isVisible = !binding.tvDate.isVisible
            binding.timepicker.isVisible = !binding.timepicker.isVisible
            binding.btnSave.isVisible = !binding.btnSave.isVisible
            binding.etDescription.isVisible = !binding.etDescription.isVisible

            if (binding.etTitle.isVisible) {
                binding.btnAddAppointment.setText(R.string.close_input)
            } else {
                binding.btnAddAppointment.setText(R.string.add_appointment)
            }
        }



        /* die "eventslist" wird überwacht und die Termine werden durch die Funktion "filterByDate"
        gefiltert. Es werden nur die Termine angezeigt von dem Tag auf dem geklickt wird.
         */
        cozyCareViewModel.eventsList.observe(viewLifecycleOwner) {
            val filteredEvents = filterByDate(it, formattedDate)
            if (filteredEvents.isEmpty()) {
                binding.rvCalendar.adapter = CalendarAdapter(filteredEvents, cozyCareViewModel)
            }
            for (event in filteredEvents) {
                binding.rvCalendar.adapter = CalendarAdapter(filteredEvents, cozyCareViewModel)
            }
        }
    }
    private fun filterByDate(events: List<Events>, date: String): List<Events> {
        return events.filter { it.date == date }
    }
}
