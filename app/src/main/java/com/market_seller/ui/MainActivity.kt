package com.market_seller.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.market_seller.R
import com.market_seller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        setupWithNavController(binding.bottomNav, navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            when (destination.id)
            {
                R.id.splashFragment, R.id.loginFragment, R.id.registerFragment, R.id.forgetPasswordFragment -> binding.bottomNav.visibility = View.GONE
                else -> binding.bottomNav.visibility = View.VISIBLE
            }

        }
    }
}