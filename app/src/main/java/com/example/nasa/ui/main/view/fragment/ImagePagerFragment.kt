package com.example.nasa.ui.main.view.fragment // ktlint-disable package-name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.nasa.databinding.FragmentPagerBinding
import com.example.nasa.ui.base.BaseFragment
import com.example.nasa.ui.main.adapter.ImagePagerAdapter
import com.example.nasa.ui.main.viewmodel.HomeViewModel
import com.example.nasa.util.ZoomOutPageTransformer

class ImagePagerFragment : BaseFragment() {
    private lateinit var binding: FragmentPagerBinding
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerBinding.inflate(inflater)
        binding.viewPager.adapter = ImagePagerAdapter(requireActivity(), homeViewModel.nasaImageDta)
        binding.viewPager.setCurrentItem(homeViewModel.currentPosition, false)
        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                homeViewModel.currentPosition = position
                super.onPageSelected(position)
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
