package de.syntax.androidabschluss.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.syntax.androidabschluss.data.model.AppPreview
import de.syntax.androidabschluss.databinding.Dayplancarousel1Binding
import de.syntax.androidabschluss.ui.home.dayPlan.DayPlanFragmentDirections

class DayPlanAdapter (private var appPreviewList: List<AppPreview>
): RecyclerView.Adapter<DayPlanAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: Dayplancarousel1Binding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            Dayplancarousel1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return appPreviewList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val preview = appPreviewList[position]
        holder.binding.apply {
            Glide.with(carousel1).load(preview.image).into(carousel1)
        }
        holder.binding.carousel1.setOnClickListener {
        val navController = holder.binding.carousel1.findNavController()
            navController.navigate(DayPlanFragmentDirections.actionDayPlanFragmentToDayPlanDetailFragment())
        }
    }
}