package com.example.nasa.ui.main.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.nasa.R
import com.example.nasa.data.model.NasaImageResponseItem
import com.example.nasa.databinding.ImageCardBinding
import com.example.nasa.ui.main.view.fragment.HomeFragment
import com.example.nasa.ui.main.viewmodel.HomeViewModel
import com.example.nasa.util.Extension.fileName
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.Any
import kotlin.Boolean
import kotlin.Int

class NasaImageAdapter(
    private val fragment: Fragment,
    private val context: Context,
    private val nasaImageDta: ArrayList<NasaImageResponseItem>
) :
    RecyclerView.Adapter<NasaImageAdapter.ImageViewHolder>() {
    private val requestManager: RequestManager = Glide.with(context)
    lateinit var listener: ItemClickListener

    inner class ImageViewHolder(var binding: ImageCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: NasaImageResponseItem) {
            setImage(item)
            binding.apply {
                cardImage.transitionName = item.url.fileName()
            }
            binding.root.setOnClickListener {
                listener.onItemClickListener(adapterPosition, item, binding.cardImage)
            }
        }

        private fun setImage(item: NasaImageResponseItem) {
            requestManager
                .load(item.url)
                .placeholder(R.drawable.image_placeholder)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        fragment.startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        fragment.startPostponedEnterTransition()
                        return false
                    }
                })
                .into(binding.cardImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.image_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind(nasaImageDta[position])
    }

    override fun getItemCount(): Int {
        return nasaImageDta.size
    }

    interface ItemClickListener {
        fun onItemClickListener(position: Int, item: NasaImageResponseItem, imageView: ImageView)
    }
}
