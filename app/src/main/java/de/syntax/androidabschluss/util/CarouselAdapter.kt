package de.syntax.androidabschluss.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.syntax.androidabschluss.data.model.AppPreview
import de.syntax.androidabschluss.databinding.CarouselItemBinding

class CarouselAdapter (private var appPreviewList: List<AppPreview>
): RecyclerView.Adapter<CarouselAdapter.AppViewHolder>() {

    class AppViewHolder(val binding: CarouselItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = CarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return appPreviewList.size
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val preview = appPreviewList[position]
        holder.binding.apply {
            Glide.with(previewImage).load(preview.image).into(previewImage)
            previewName.text = preview.name
        }
    }
}