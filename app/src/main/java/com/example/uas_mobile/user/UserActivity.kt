package com.example.uas_mobile.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.uas_mobile.PrefManager
import com.example.uas_mobile.R
import com.example.uas_mobile.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val navController = findNavController(R.id.user_nav_host)

        binding.userBottomNavigationView.setupWithNavController(navController)

        with(binding) {
            val username = PrefManager.getInstance(this@UserActivity).getUsername().toString()
            textGreeting.text = "Hi $username, Pesan Sekarang?"
        }
    }

    companion object {
        fun getLaunchService(from: Context) = Intent(from, UserActivity::class.java)
    }
}