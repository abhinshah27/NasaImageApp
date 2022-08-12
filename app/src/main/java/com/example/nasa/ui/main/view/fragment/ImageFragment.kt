package com.example.nasa.ui.main.view.fragment // ktlint-disable package-name

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.nasa.R
import com.example.nasa.databinding.FragmentImageBinding
import com.example.nasa.ui.base.BaseFragment
import com.example.nasa.ui.main.viewmodel.HomeViewModel
import com.example.nasa.util.Constants
import com.example.nasa.util.Extension.fileName
import java.text.SimpleDateFormat
import java.util.*

class ImageFragment : BaseFragment() {
    private val homeViewModel: HomeViewModel by activityViewModels()

    companion object {
        fun newInstance(position: Int): ImageFragment {
            val fragment = ImageFragment()
            val argument = Bundle()
            argument.putInt(Constants.CURRENT_POSITION, position)
            fragment.arguments = argument
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentImageBinding.inflate(inflater)

        val arguments = arguments
        val position = arguments?.getInt(Constants.CURRENT_POSITION)
        position?.let {
            binding.image.transitionName = homeViewModel.nasaImageDta?.get(it)?.url.fileName()
            val imageUrl = homeViewModel.nasaImageDta?.get(it)?.url
            val imageTitle = homeViewModel.nasaImageDta?.get(it)?.title
            val imageDes = homeViewModel.nasaImageDta?.get(it)?.explanation
            val imageDate = homeViewModel.nasaImageDta?.get(it)?.date
            val parser = SimpleDateFormat(Constants.INPUT_DATE_FORMAT_YYYY_MM_DD, Locale("IN"))
            val formatter = SimpleDateFormat(Constants.OUTPUT_DATE_FORMAT_DD_MMM_YYY, Locale("IN"))
            imageDate?.let { date ->
                val oldFormattedDate = parser.parse(date)
                oldFormattedDate?.let { oldDate ->
                    val dateToShow: String = formatter.format(oldDate)
                    binding.tvDate.text = dateToShow
                }
            }
            binding.tvTitle.text = imageTitle
            binding.tvDescription.text = imageDes

            //Set the unique transition name for each images.
            binding.image.transitionName = imageUrl.fileName()
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        //Once the image loaded start transition
                        requireParentFragment().startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        //Once the image loaded start transition
                        requireParentFragment().startPostponedEnterTransition()
                        return false
                    }
                })
                .into(binding.image)
        }

        return binding.root
    }
}
