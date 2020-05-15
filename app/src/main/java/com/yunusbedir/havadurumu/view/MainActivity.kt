package com.yunusbedir.havadurumu.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.libraries.places.api.Places
import com.yunusbedir.havadurumu.R

class MainActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController
    private val API_KEY = "AIzaSyCkgnfx2M2M5Ugz6L8tytTRsYb55iBOoAo"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
        setupPlaces()
    }

    private fun setupPlaces() {
        Places.initialize(this, API_KEY)
        Places.createClient(this)
    }

    private fun setupNavigation() {
        navigationController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navigationController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navigationController, null)

    }
}
