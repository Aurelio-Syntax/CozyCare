package de.syntax.androidabschluss.ui.home.notes

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.model.Notes
import de.syntax.androidabschluss.databinding.FragmentEditNoteBinding
import de.syntax.androidabschluss.ui.authentication.viewmodel.FireBaseViewModel
import java.text.SimpleDateFormat
import java.util.Date


class EditNoteFragment : Fragment() {
    private lateinit var binding: FragmentEditNoteBinding
    private val viewModel: FireBaseViewModel by activityViewModels()
    private var currentDate: String? = null

    private var noteId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            noteId = it.getString("noteId").toString()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())


        Log.d("EditNoteFragment", "Edit Note onviewCreated")
        noteId.let {
            viewModel.getNotesById(it).observe(viewLifecycleOwner) { notes ->
                Log.d("EditNoteFragment", "Note before id: $noteId")
                if (notes != null) {
                    Log.d("EditNoteFragment", "Note with id: $noteId")
                    binding.editNoteTitle.setText(notes.title)
                    binding.editNoteDesc.setText(notes.noteText)
                    binding.tvEditDateTime.text = notes.dateTime
                    binding.editNoteSubTitle.setText(notes.subTitle)
                }
            }
        }
        binding.editNoteBack.setOnClickListener {
            findNavController().navigate(EditNoteFragmentDirections.actionEditNoteFragmentToNotesFragment())
        }

        // animiertes Icon mit Glide
        val imgdelete = binding.imgDelete
        Glide.with(this)
            .load(R.drawable.trashbin_anim)
            .into(imgdelete)


        binding.imgDelete.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(R.string.deleteButton)
                .setCancelable(false)
                .setPositiveButton(R.string.yes) { dialog, _ ->
                    // Löschen der Note
                    val noteId = noteId// Get the note ID
                    viewModel.deleteNote(noteId) // deleteNote Funktion aus dem ViewModel
                    findNavController().navigate(R.id.notesFragment)
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.cancel()
                }
            val alert = builder.create()
            alert.show()
        }


        binding.editNoteTick.setOnClickListener {
            val updatedNote = Notes(
                id = noteId,
                title = binding.editNoteTitle.text.toString(),
                noteText = binding.editNoteDesc.text.toString(),
                dateTime = currentDate.toString(),// Aktuelles Datum und Uhrzeit
                subTitle = binding.editNoteSubTitle.text.toString()
            )
            viewModel.updateNote(updatedNote)
            findNavController().navigate(EditNoteFragmentDirections.actionEditNoteFragmentToNotesFragment())
        }
    }
}