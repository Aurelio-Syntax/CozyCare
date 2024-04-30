package de.syntax.androidabschluss.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.model.Notes
import de.syntax.androidabschluss.databinding.NotesItemBinding
import de.syntax.androidabschluss.ui.authentication.viewmodel.FireBaseViewModel
import de.syntax.androidabschluss.ui.home.notes.NotesFragmentDirections

class NotesAdapter (
    private val dataset: List<Notes>,
    private val context: Context,
    private val viewModel: FireBaseViewModel
) : RecyclerView.Adapter<NotesAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: NotesItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = NotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val notes = dataset[position]


        holder.binding.tvNotesTitle.text = notes.title
        holder.binding.tvNotesDesc.text = notes.noteText
        holder.binding.tvNotesDateTime.text = notes.dateTime


        holder.binding.cvNotes.setCardBackgroundColor(notes.color)

        holder.binding.cvNotes.setOnClickListener {
            val navController = holder.binding.cvNotes.findNavController()
            navController.navigate(NotesFragmentDirections.actionNotesFragmentToEditNoteFragment(noteId = notes.id))
        }
        // Löschen nach langem drücken auf Notiz mit BestätigungsAbfrage (alert dialog)
        holder.binding.cvNotes.setOnLongClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(R.string.deleteButton)
                .setCancelable(false)
                .setPositiveButton(R.string.yes) { _, _ ->
                    val noteId = notes.id
                    viewModel.deleteNote(noteId)
                    val navController = holder.binding.cvNotes.findNavController()
                    navController.navigate(NotesFragmentDirections.actionNotesFragmentSelf())
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.cancel()
                }
            val alert = builder.create()
            alert.show()
            return@setOnLongClickListener true
        }
    }
}