package de.syntax.androidabschluss.ui.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.syntax.androidabschluss.databinding.FragmentAllEventsBinding
import de.syntax.androidabschluss.util.AllEventsAdapter
import de.syntax.androidabschluss.viewmodel.CozyCareViewModel


class AllEventsFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAllEventsBinding
    private val cozyCareViewModel: CozyCareViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllEventsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Überwacht die "eventslist" (Datenbank mit allen Terminen des Kalendars) und
         übergibt sie an den Adapter wo die Daten an die Textviews ect gebunden werden
         um in der Recyclerview (rvAllEvents) angezeigt zu werden
         */
        cozyCareViewModel.eventsList.observe(viewLifecycleOwner) {
            binding.rvAllEvents.adapter = AllEventsAdapter(it, cozyCareViewModel)
        }
    }


}