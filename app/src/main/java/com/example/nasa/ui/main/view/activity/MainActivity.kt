package com.example.nasa.ui.main.view.activity // ktlint-disable package-name

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.nasa.R
import com.example.nasa.databinding.ActivityMainBinding
import com.example.nasa.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.findFragmentById(R.id.nav_user_access)?.findNavController()
    }

    /**
     * exit app from home screen
     */
    override fun onBackPressed() {
        val navController: NavController = Navigation.findNavController(this, R.id.nav_user_access)
        val id = navController.currentDestination!!.id
        if (id == R.id.fragment_home) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
