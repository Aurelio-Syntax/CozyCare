package de.syntax.androidabschluss.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.data.model.Events
import de.syntax.androidabschluss.databinding.CalendarItemBinding
import de.syntax.androidabschluss.viewmodel.CozyCareViewModel


class CalendarAdapter(
    private var dataset: List<Events>,
    private var viewModel: CozyCareViewModel
) : RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: CalendarItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CalendarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val events = dataset[position]

        holder.binding.tvAppointmentName.text = events.name
        holder.binding.tvAppointmentDesc.text = events.description
        holder.binding.tvAppointmentTime.text = events.time
        holder.binding.btnDelete.setOnClickListener {
            viewModel.deleteEvent(events)
        }
    }
}