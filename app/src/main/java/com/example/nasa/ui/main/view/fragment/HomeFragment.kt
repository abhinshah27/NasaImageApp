package com.example.nasa.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.nasa.databinding.FragmentHomeBinding
import com.example.nasa.ui.base.BaseFragment
import com.example.nasa.ui.main.adapter.NasaImageAdapter
import com.example.nasa.ui.main.viewmodel.HomeViewModel

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        binding.recyclerView.adapter = homeViewModel.nasaImageDta?.let { NasaImageAdapter(this@HomeFragment, it, homeViewModel) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollToPosition() // scroll to particular position once ex: we change the position of data inside details listing it will automatically change grid position
    }

    /**
     * scroll to last selected position in pager screen
     */
    private fun scrollToPosition() {
        binding.recyclerView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                binding.recyclerView.removeOnLayoutChangeListener(this)
                val layoutManager = binding.recyclerView.layoutManager
                val viewAtPosition =
                    layoutManager!!.findViewByPosition(homeViewModel.currentPosition)

                if (viewAtPosition == null || layoutManager.isViewPartiallyVisible(
                        viewAtPosition,
                        false,
                        true
                    )
                ) {
                    binding.recyclerView.post { layoutManager.scrollToPosition(homeViewModel.currentPosition) }
                }
            }
        })
    }
}
