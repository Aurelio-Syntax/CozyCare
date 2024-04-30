package de.syntax.androidabschluss.ui.home.notes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.model.Notes
import de.syntax.androidabschluss.databinding.FragmentNotesBinding
import de.syntax.androidabschluss.ui.authentication.viewmodel.FireBaseViewModel
import de.syntax.androidabschluss.util.NotesAdapter

class NotesFragment : Fragment() {
    private val viewModel: FireBaseViewModel by activityViewModels()
    private lateinit var binding: FragmentNotesBinding

    private var currentQuery = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // animiertes Icon mit Glide
        val floatingActionButton = binding.imgFab
        Glide.with(this)
            .load(R.drawable.pen_anim2)
            .into(floatingActionButton)

        binding.imgFab.setOnClickListener {
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToCreateNoteFragment())
        }

        viewModel.notesCollection.observe(viewLifecycleOwner) { snapshot ->
            if (snapshot != null) {
                val notesList = mutableListOf<Notes>()
                for (document in snapshot.documents) {
                    val notes = document.toObject(Notes::class.java)
                    notesList.add(notes!!)
                }

                Log.e("NotesFragment", "Retrieved: ${notesList.size} notes.")
                binding.rvNotes.adapter = NotesAdapter(notesList, requireContext(), viewModel)

            } else {
                Log.e("NotesFragment", "Out Data is null!")
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                currentQuery = newText
                updateNotesList(currentQuery)
                return true
            }
        })
    }
    private fun updateNotesList(query: String) {
        val filteredList = mutableListOf<Notes>()
        if (query.isEmpty()) {
            filteredList.addAll(viewModel.notesCollection.value!!.toObjects(Notes::class.java))
        } else {
            for (note in viewModel.notesCollection.value!!.toObjects(Notes::class.java)) {
                if (note.title.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(note)
                }
            }
        }
        binding.rvNotes.adapter = NotesAdapter(filteredList, requireContext(), viewModel)
    }
}