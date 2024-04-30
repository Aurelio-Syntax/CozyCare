package de.syntax.androidabschluss.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.data.model.Item
import de.syntax.androidabschluss.databinding.YoutubeItemBinding
import de.syntax.androidabschluss.ui.home.meditation.RelaxFragment

class YoutubeAdapter(
    private val dataset: List<Item>,
    private val fragment: RelaxFragment
): RecyclerView.Adapter<YoutubeAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: YoutubeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = YoutubeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        holder.binding.imgThumbnail.load(item.snippet.thumbnails.medium.url)
        holder.binding.tvVideoTitle.text = item.snippet.title
        holder.binding.tvVideoDescription.text = item.snippet.description
        holder.binding.imgThumbnail.setOnClickListener {
            item.id.videoId?.let { it1 -> fragment.updateVideo(it1) }
        }
    }
}