package de.syntax.androidabschluss.ui.home.dayPlan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.model.DiaryEntries
import de.syntax.androidabschluss.databinding.FragmentCreateDiaryEntryBinding
import de.syntax.androidabschluss.viewmodel.CozyCareViewModel


class CreateDiaryEntryFragment : Fragment() {
    private lateinit var binding: FragmentCreateDiaryEntryBinding
    private val cozyCareViewModel: CozyCareViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateDiaryEntryBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.createEntryBack.setOnClickListener {
            findNavController().navigate(CreateDiaryEntryFragmentDirections.actionCreateDiaryEntryFragmentToGratefulFragment())
        }

        binding.createEntryTick.setOnClickListener {
            val diaryEntry = DiaryEntries(
                id = 0,
                title = binding.createEntryTitle.text.toString(),
                text = binding.createEntryDesc.text.toString()
            )
            if (binding.createEntryDesc.text.isNullOrEmpty()) {
                Toast.makeText(context, R.string.description_required, Toast.LENGTH_SHORT).show()
            } else {
                cozyCareViewModel.insertDiaryEntry(diaryEntry)
                findNavController().navigate(CreateDiaryEntryFragmentDirections.actionCreateDiaryEntryFragmentToGratefulFragment())
            }
        }
    }


}