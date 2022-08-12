package com.example.nasa.ui.main.view.fragment // ktlint-disable package-name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.nasa.R
import com.example.nasa.databinding.FragmentImageBinding
import com.example.nasa.ui.base.BaseFragment
import com.example.nasa.ui.main.viewmodel.HomeViewModel
import com.example.nasa.util.Constants
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

            binding.image.transitionName = imageUrl.toString()
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.image)
        }

        return binding.root
    }
}
