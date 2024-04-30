package de.syntax.androidabschluss.ui.home.dayPlan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.model.Tasks
import de.syntax.androidabschluss.databinding.FragmentBottomSheetTaskBinding
import de.syntax.androidabschluss.viewmodel.CozyCareViewModel


class BottomSheetTaskFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetTaskBinding
    private val cozyCareViewModel: CozyCareViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Speichern der Aufgabe im "tasks_table"
        binding.btnSaveTodo.setOnClickListener {
            val task = Tasks(
                id = 0,
                text = binding.editTask.text.toString(),
                checked = false

            ) // Wenn kein Text eingegeben wurde, wird ein Toast ausgegeben

            if (binding.editTask.text.isNullOrEmpty()) {
                Toast.makeText(context, R.string.description_required, Toast.LENGTH_SHORT).show()
            } else {
                cozyCareViewModel.insertTask(task)
                findNavController().navigate(BottomSheetTaskFragmentDirections.actionBottomSheetTaskFragmentToToDoFragment())
            }
        }
    }




}