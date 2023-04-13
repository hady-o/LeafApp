package com.example.leafapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.leafapp.databinding.ActivityMainBinding
import com.example.leafapp.dataclass.PostClass
import com.example.leafapp.ui.DetalsFragment
import com.example.leafapp.ui.ProfileFragmentDirections
import com.example.leafapp.ui.SplashFragmentDirections
import com.example.leafapp.ui.home.homemenus.HistoryFragment
import com.example.leafapp.utils.setLocale

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DiseasesData.loadData(assets)
        setLocale(SharedPref.language)


    }


        // NavigationUI.setupActionBarWithNavController(this, navController)
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}