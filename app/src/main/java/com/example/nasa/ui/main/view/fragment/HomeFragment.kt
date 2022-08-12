package com.example.nasa.ui.main.view.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.nasa.R
import com.example.nasa.data.model.NasaImageResponseItem
import com.example.nasa.databinding.FragmentHomeBinding
import com.example.nasa.ui.base.BaseFragment
import com.example.nasa.ui.main.adapter.NasaImageAdapter
import com.example.nasa.ui.main.viewmodel.HomeViewModel
import com.example.nasa.util.Extension.fileName

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
        binding.recyclerView.adapter = homeViewModel.nasaImageDta?.let {
            NasaImageAdapter(
                this@HomeFragment,
                requireContext(),
                it
            )
        }
        (binding.recyclerView.adapter as NasaImageAdapter).listener =
            object : NasaImageAdapter.ItemClickListener {
                override fun onItemClickListener(
                    position: Int,
                    item: NasaImageResponseItem,
                    imageView: ImageView
                ) {
                    homeViewModel.currentPosition = position
                    //Add a transition view to the navigation
                    val extras = FragmentNavigatorExtras(
                        imageView to item.url.fileName()
                    )
                    findNavController().navigate(
                        R.id.action_fragment_home_to_fragment_image_pager,
                        null,
                        null,
                        extras
                    )
                }

            }

        prepareTransitions()
        postponeEnterTransition()
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
                val layoutManager: RecyclerView.LayoutManager = binding.recyclerView.layoutManager!!
                val viewAtPosition = layoutManager.findViewByPosition(homeViewModel.currentPosition)
                // Scroll to position if the view for the current position is null (not currently part of
                // layout manager children), or it's not completely visible.
                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)
                ) {
                    binding.recyclerView.post(Runnable {
                        layoutManager.scrollToPosition(
                            homeViewModel.currentPosition!!
                        )
                    })
                }
            }
        })
    }

    private fun prepareTransitions() {
        sharedElementReturnTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.grid_exit_transition)
    }
}
