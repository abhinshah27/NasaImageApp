package com.example.nasa.ui.main.view.fragment // ktlint-disable package-name

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.nasa.R
import com.example.nasa.databinding.FragmentPagerBinding
import com.example.nasa.ui.base.BaseFragment
import com.example.nasa.ui.main.adapter.ImagePagerAdapter
import com.example.nasa.ui.main.viewmodel.HomeViewModel
import com.example.nasa.util.Extension.fileName
import com.example.nasa.util.ZoomOutPageTransformer

class ImagePagerFragment : BaseFragment() {
    private lateinit var binding: FragmentPagerBinding
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Set enter transition
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerBinding.inflate(inflater)

        binding.viewPager.adapter = ImagePagerAdapter(this, homeViewModel.nasaImageDta)
        binding.viewPager.setCurrentItem(homeViewModel.currentPosition, false)
        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                homeViewModel.currentPosition = position
                super.onPageSelected(position)
            }
        })

        if (savedInstanceState == null) {
            postponeEnterTransition()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
