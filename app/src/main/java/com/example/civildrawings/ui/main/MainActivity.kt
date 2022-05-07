package com.example.civildrawings.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.civildrawings.R
import com.example.civildrawings.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val botNavView: BottomNavigationView = binding.botNavView
        val navController = findNavController(R.id.nav_host_fragment)

        val toolBar= binding.toolbar
        setSupportActionBar(toolBar)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.mapsFragment, R.id.sitesFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        botNavView.setupWithNavController(navController)

    }
}