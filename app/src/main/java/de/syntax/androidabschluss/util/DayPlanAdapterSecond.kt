package de.syntax.androidabschluss.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.syntax.androidabschluss.data.model.MeditationPreview
import de.syntax.androidabschluss.databinding.Dayplancarousel2Binding
import de.syntax.androidabschluss.ui.home.dayPlan.DayPlanFragmentDirections

class DayPlanAdapterSecond (private var meditationPreviewList: List<MeditationPreview>
): RecyclerView.Adapter<DayPlanAdapterSecond.ItemViewHolder>() {

    class ItemViewHolder(val binding: Dayplancarousel2Binding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            Dayplancarousel2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return meditationPreviewList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val preview = meditationPreviewList[position]

        holder.binding.apply {
            Glide.with(carousel2).load(preview.image).into(carousel2)

            holder.binding.carousel2.setOnClickListener {
                val navController = holder.binding.carousel2.findNavController()
                navController.navigate(DayPlanFragmentDirections.actionDayPlanFragmentToGratefulFragment())
            }
        }
    }
}