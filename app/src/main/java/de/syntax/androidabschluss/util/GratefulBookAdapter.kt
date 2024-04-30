package de.syntax.androidabschluss.util

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.model.DiaryEntries
import de.syntax.androidabschluss.databinding.GratefulItemBinding
import de.syntax.androidabschluss.ui.home.dayPlan.GratefulFragmentDirections
import de.syntax.androidabschluss.viewmodel.CozyCareViewModel

class GratefulBookAdapter (
    private val dataset: List<DiaryEntries>,
    private val context: Context,
    private val viewModel: CozyCareViewModel
): RecyclerView.Adapter <GratefulBookAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: GratefulItemBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = GratefulItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val entries = dataset[position]

        holder.binding.cvGrateful.setCardBackgroundColor(Color.LTGRAY)

        holder.binding.tvGratefulTitle.text = entries.title
        holder.binding.tvGratefulDesc.text = entries.text

        holder.binding.cvGrateful.setOnLongClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(R.string.deleteButton)
                .setCancelable(false)
                .setPositiveButton(R.string.yes) { _, _ ->
                    viewModel.deleteDiaryEntry(entries)
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.cancel()
                }
            val alert = builder.create()
            alert.show()
            return@setOnLongClickListener true
        }

        holder.binding.cvGrateful.setOnClickListener {
            val navController = holder.binding.cvGrateful.findNavController()
            navController.navigate(GratefulFragmentDirections.actionGratefulFragmentToEditDiaryEntryFragment(entryId = entries.id))
        }
    }
}