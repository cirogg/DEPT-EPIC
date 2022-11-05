package com.cirogg.deptepicchallenge.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cirogg.deptepicchallenge.R
import com.cirogg.deptepicchallenge.databinding.ItemGridImageBinding
import com.cirogg.deptepicchallenge.model.response.DatesResponse
import com.cirogg.deptepicchallenge.model.response.ImagesResponse
import com.cirogg.deptepicchallenge.utils.Const
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<ImagesResponse>(){
        override fun areItemsTheSame(
            oldItem: ImagesResponse,
            newItem: ImagesResponse
        ): Boolean {
            return oldItem.identifier == newItem.identifier
        }

        override fun areContentsTheSame(
            oldItem: ImagesResponse,
            newItem: ImagesResponse
        ): Boolean {
            return oldItem == newItem
        }

    }

    val diff = AsyncListDiffer(this,differCallback)

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        diff.currentList[position].let { photoItem -> holder.bind(photoItem) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemGridImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    inner class ImageViewHolder(private val binding: ItemGridImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: ImagesResponse) {
            binding.run {
                Picasso.get().load("${Const.IMAGE_URL}${image.getCleanDate()}/jpg/${image.image}.jpg")
                    .placeholder(R.drawable.ic_palceholder)
                    .into(binding.photoGrid, object : Callback {
                        override fun onSuccess() {

                        }

                        override fun onError(e: Exception?) {

                        }
                    })
            }
        }

        }
}