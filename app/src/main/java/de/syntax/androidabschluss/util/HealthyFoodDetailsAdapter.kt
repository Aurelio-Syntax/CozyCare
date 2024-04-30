package de.syntax.androidabschluss.util


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.data.model.ExtendedIngredient
import de.syntax.androidabschluss.databinding.HealthyfooddetailItemBinding

class HealthyFoodDetailsAdapter (
    private val dataset: List<ExtendedIngredient>,
): RecyclerView.Adapter<HealthyFoodDetailsAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: HealthyfooddetailItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = HealthyfooddetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        holder.binding.tvIngredientName.text = item.name
        holder.binding.tvRecipeAmount.text = item.measures.metric.amount.toString()
        holder.binding.tvUnit.text = item.measures.metric.unitShort
    }


}