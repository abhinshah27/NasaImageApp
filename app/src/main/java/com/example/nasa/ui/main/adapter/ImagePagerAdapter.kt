package com.example.nasa.ui.main.adapter // ktlint-disable package-name

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nasa.data.model.NasaImageResponseItem
import com.example.nasa.ui.main.view.fragment.ImageFragment

class ImagePagerAdapter(
    fa: Fragment,
    private val nasaImageDta: ArrayList<NasaImageResponseItem>?
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = nasaImageDta?.size ?: 0

    override fun createFragment(position: Int): Fragment = ImageFragment.newInstance(position)
}
