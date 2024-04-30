package de.syntax.androidabschluss.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.model.Tasks
import de.syntax.androidabschluss.databinding.TaskTodoItemBinding
import de.syntax.androidabschluss.viewmodel.CozyCareViewModel

class ToDoAdapter (
    private val dataset: List<Tasks>,
    private val context: Context,
    private val cozyCareViewModel: CozyCareViewModel
): RecyclerView.Adapter<ToDoAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: TaskTodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = TaskTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val tasks = dataset[position]


        holder.binding.tvTaskDesc.text = tasks.text
        holder.binding.todoCheckBox.isChecked = tasks.checked
        holder.binding.todoCheckBox.setOnCheckedChangeListener { _, isChecked ->
            tasks.checked = isChecked
            cozyCareViewModel.updateTask(tasks)
        }

        // Löschen der Notiz mit langem Klick auf das Item
        holder.binding.cvTodo.setOnLongClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(R.string.deleteButton)
                .setCancelable(false)
                .setPositiveButton(R.string.yes) { _, _ ->
                    cozyCareViewModel.deleteTask(tasks)
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.cancel()
                }
            val alert = builder.create()
            alert.show()
            return@setOnLongClickListener true
        }

    }
    /* Funktion um die Position des Items zu erhalten/ Diese wird im TodoFragment
    genutzt um mit wischgeste zu löschen
     */
    fun getTaskPosition(position: Int): Tasks {
        return dataset[position]
    }
    fun getContext(): Context {
        return this.context
    }
}
