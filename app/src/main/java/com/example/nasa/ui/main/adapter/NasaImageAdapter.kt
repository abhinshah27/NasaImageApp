package com.example.nasa.ui.main.adapter

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
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.Any
import kotlin.Boolean
import kotlin.Int

class NasaImageAdapter(
    gridFragment: HomeFragment,
    private val nasaImageDta: ArrayList<NasaImageResponseItem>,
    homeViewModel: HomeViewModel
) :
    RecyclerView.Adapter<NasaImageAdapter.ImageViewHolder>() {
    private val requestManager: RequestManager = Glide.with(gridFragment)
    private val viewHolderListener: ViewHolderListener = ViewHolderListenerImpl(gridFragment, homeViewModel)

    class ViewHolderListenerImpl(private val fragment: Fragment, private val homeViewModel: HomeViewModel) : ViewHolderListener {
        private val enterTransitionStarted: AtomicBoolean = AtomicBoolean()
        override fun onLoadCompleted(view: ImageView, adapterPosition: Int) {
            if (homeViewModel.currentPosition != adapterPosition) {
                return
            }
            if (enterTransitionStarted.getAndSet(true)) {
                return
            }
            fragment.startPostponedEnterTransition()
        }

        override fun onItemClicked(view: View, adapterPosition: Int) {
            homeViewModel.currentPosition = adapterPosition
            view.findNavController().navigate(R.id.action_fragment_home_to_fragment_image_pager)
        }
    }

    interface ViewHolderListener {
        fun onLoadCompleted(view: ImageView, adapterPosition: Int)

        fun onItemClicked(view: View, adapterPosition: Int)
    }

    inner class ImageViewHolder(
        var binding: ImageCardBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            viewHolderListener.onItemClicked(v, adapterPosition)
        }

        fun onBind() {
            val adapterPosition = adapterPosition
            setImage(adapterPosition)
            binding.apply {
                cardImage.transitionName = nasaImageDta[adapterPosition].toString()
            }
        }

        private fun setImage(adapterPosition: Int) {
            requestManager
                .load(nasaImageDta[adapterPosition].url)
                .placeholder(R.drawable.image_placeholder)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        viewHolderListener.onLoadCompleted(binding.cardImage, adapterPosition)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        viewHolderListener.onLoadCompleted(binding.cardImage, adapterPosition)
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
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return nasaImageDta.size
    }
}
