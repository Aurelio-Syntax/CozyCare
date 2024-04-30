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
import de.syntax.androidabschluss.databinding.FragmentUpdateTaskBinding
import de.syntax.androidabschluss.viewmodel.CozyCareViewModel


class UpdateTaskFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentUpdateTaskBinding
    private val viewModel: CozyCareViewModel by activityViewModels()

    // Für die Übergabe aus dem TodoFragment
    private var taskId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Übergabe aus dem TodoFragment
        arguments?.let {
            taskId = it.getInt("taskId")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /* Der erstellte Task im TodoFragment wird aus der tasksliste per id die
        übergeben wurde rausgesucht und der Task mit der richtigen id am Edittext "editTodoTask" gebunden
         */
        val task = viewModel.tasksList.value?.find { it.id == taskId }

        if (task != null) {
            binding.editTodoTask.setText(task.text)
        }
        // Speichern des überarbeiteten Tasks
        binding.btnSaveEditToDo.setOnClickListener {
            val task = Tasks(
                id = taskId,
                text = binding.editTodoTask.text.toString(),
                checked = false
            )
            if (binding.editTodoTask.text.isEmpty()) {
                Toast.makeText(context, R.string.description_required, Toast.LENGTH_SHORT).show()
            } else {
                viewModel.updateTask(task)
                findNavController().navigate(UpdateTaskFragmentDirections.actionUpdateTaskFragmentToToDoFragment())
            }
        }
    }
}