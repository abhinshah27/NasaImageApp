package com.example.nasa.ui.base // ktlint-disable package-name

import android.content.Context
import androidx.fragment.app.Fragment

/**
 * Created by Abhin.
 */
abstract class BaseFragment : Fragment() {

    var mBaseActivity: BaseActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mBaseActivity = context
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
