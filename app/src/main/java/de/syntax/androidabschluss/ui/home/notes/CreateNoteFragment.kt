package de.syntax.androidabschluss.ui.home.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.model.Notes
import de.syntax.androidabschluss.databinding.FragmentCreateNoteBinding
import de.syntax.androidabschluss.ui.authentication.viewmodel.FireBaseViewModel
import java.text.SimpleDateFormat
import java.util.Date


class CreateNoteFragment : Fragment() {
    private val viewModel: FireBaseViewModel by activityViewModels()
    private lateinit var binding: FragmentCreateNoteBinding
    private var currentDate: String? = null


    val noteColors = listOf(
        R.color.lightBlue,
        R.color.lightOrange,
        R.color.lightGreen,
        R.color.lightYellow,
        R.color.lightRed
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        binding.tvCreateDateTime.text = currentDate

        binding.createNoteTick.setOnClickListener {
            val title = binding.createNoteTitle.text.toString()
            val subTitle = binding.createNoteSubTitle.text.toString()
            val dateTime = binding.tvCreateDateTime.text.toString()
            val noteText = binding.createNoteDesc.text.toString()

            val color = noteColors.random()
            val randomColor = context?.let { it1 -> ContextCompat.getColor(it1, color) }

            val notes =
                randomColor?.let { it1 -> Notes(id = "", title, subTitle, dateTime, noteText, it1) }

            if (binding.createNoteTitle.text.isNullOrEmpty()) {
                Toast.makeText(context, R.string.titel_required, Toast.LENGTH_SHORT).show()
            } else if (binding.createNoteDesc.text.isNullOrEmpty()) {
                Toast.makeText(context, R.string.description_required, Toast.LENGTH_SHORT).show()
            } else {
                if (notes != null) {
                    viewModel.addNote(notes)
                }
                findNavController().navigate(CreateNoteFragmentDirections.actionCreateNoteFragmentToNotesFragment())
            }


        }

        binding.createNoteBack.setOnClickListener {
            findNavController().navigate(CreateNoteFragmentDirections.actionCreateNoteFragmentToNotesFragment())
        }

    }
}