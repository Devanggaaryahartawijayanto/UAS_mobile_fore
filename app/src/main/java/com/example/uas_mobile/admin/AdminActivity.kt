package com.example.uas_mobile.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.uas_mobile.R
import com.example.uas_mobile.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAdminBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val navController = findNavController(R.id.admin_nav_host)

        binding.adminBottomNavigationView.setupWithNavController(navController)
    }

    companion object{
        fun getLaunchService(from: Context) = Intent(from, AdminActivity::class.java)
    }
}