package com.example.nasa.ui.main.view.fragment // ktlint-disable package-name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nasa.R
import com.example.nasa.ui.base.BaseFragment
import com.example.nasa.ui.main.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

class SplashFragment : BaseFragment() {

    private val homeViewModel: HomeViewModel by activityViewModels { defaultViewModelProviderFactory }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    /**
     * initialize splash code and read json data and set it in view model
     */
    private fun init() {
        val isDataAdded = homeViewModel.getJsonDataFromAsset(requireContext())

        if (isDataAdded) {
            lifecycleScope.launchWhenStarted {
                delay(1000) // start home screen after delay of 1 second once we get data from json file
                findNavController().navigate(R.id.fragment_home)
            }
        }
    }
}
