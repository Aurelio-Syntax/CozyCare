package de.syntax.androidabschluss.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.data.model.Events
import de.syntax.androidabschluss.databinding.AlleventsItemBinding
import de.syntax.androidabschluss.viewmodel.CozyCareViewModel

class AllEventsAdapter (
    private var dataset: List<Events>,
    private var viewModel: CozyCareViewModel
) : RecyclerView.Adapter<AllEventsAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: AlleventsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = AlleventsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val events = dataset[position]

        holder.binding.tvEventName.text = events.name
        holder.binding.tvEventDesc.text = events.description
        holder.binding.tvEventTime.text = events.time
        holder.binding.tvEventDate.text = events.date
        holder.binding.btnDelete.setOnClickListener {
            viewModel.deleteEvent(events)
        }
    }

}