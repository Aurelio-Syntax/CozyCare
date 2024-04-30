package de.syntax.androidabschluss.ui.home.dayPlan

import android.app.AlertDialog
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
import de.syntax.androidabschluss.data.model.DiaryEntries
import de.syntax.androidabschluss.databinding.FragmentEditDiaryEntryBinding
import de.syntax.androidabschluss.viewmodel.CozyCareViewModel


class EditDiaryEntryFragment : Fragment() {
    private lateinit var binding: FragmentEditDiaryEntryBinding
    private val cozyCareViewModel: CozyCareViewModel by activityViewModels()

    private var entryId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            entryId = it.getInt("entryId")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditDiaryEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entry = cozyCareViewModel.diaryEntriesList.value?.find { it.id == entryId }

        if (entry != null) {
            binding.editEntryTitle.setText(entry.title)
            binding.editEntryDesc.setText(entry.text)
        }

        binding.editEntryTick.setOnClickListener {
            val entry = DiaryEntries(
                id = entryId,
                title = binding.editEntryTitle.text.toString(),
                text = binding.editEntryDesc.text.toString()
            )
            if (binding.editEntryDesc.text.isEmpty()) {
                Toast.makeText(context, R.string.description_required, Toast.LENGTH_SHORT).show()
            } else {
                cozyCareViewModel.updateDiaryEntry(entry)
                findNavController().navigate(EditDiaryEntryFragmentDirections.actionEditDiaryEntryFragmentToGratefulFragment())
            }
        }
        binding.editEntryBack.setOnClickListener {
            findNavController().navigate(EditDiaryEntryFragmentDirections.actionEditDiaryEntryFragmentToGratefulFragment())
        }

        // animiertes Icon mit Glide
        val imgDelete = binding.imgEntryDelete
        Glide.with(this)
            .load(R.drawable.trashbin_anim)
            .into(imgDelete)

        // AlertDialog für bestätigungsabfrage zum Löschen des DiaryEntries
        binding.imgEntryDelete.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(R.string.deleteButton)
                .setCancelable(false)
                .setPositiveButton(R.string.yes) { dialog, _ ->
                    // Löschen des DiaryEntry
                    if (entry != null) {
                        cozyCareViewModel.deleteDiaryEntry(entry)
                    } // deleteDiaryEntry Funktion aus dem ViewModel
                    findNavController().navigate(R.id.gratefulFragment)
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.cancel()
                }
            val alert = builder.create()
            alert.show()
        }
    }

}