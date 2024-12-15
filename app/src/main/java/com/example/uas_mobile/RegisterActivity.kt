package com.example.uas_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uas_mobile.databinding.ActivityRegisterBinding
import com.example.uas_mobile.network.ApiClient
import com.example.uas_mobile.response.RegisterResponse
import com.example.uas_mobile.model.Users
import com.example.uas_mobile.welcome.WelcomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding // View Binding
    private val apiService by lazy { ApiClient.getInstance() } // Inisialisasi API Client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater) // Inisialisasi Binding
        setContentView(binding.root) // Set layout menggunakan binding

        // Tombol kembali
        binding.iconBack.setOnClickListener {
            onBackPressed() // Mengembalikan ke activity sebelumnya
        }

        with(binding) {
            var role: String? = null

            signUp.setOnClickListener {
                val name = nama.text.toString()
                val email = email.text.toString()
                val password = password.text.toString()

                // Cek apakah Admin atau User dipilih
                when {
                    admin.isChecked -> role = "ADMIN"
                    user.isChecked -> role = "USER"
                    else -> {
                        Toast.makeText(this@RegisterActivity, "Please select a role type", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }

                // Validasi input
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    register(name, email, password, role!!)
                } else {
                    Toast.makeText(this@RegisterActivity, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                }
            }

            iconBack.setOnClickListener {
                val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun register(name: String, email: String, password: String, role: String) {
        val request = Users(name, email, password, role)

        // Panggil API
        apiService.createUser(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success) {
                        // Sukses
                        Toast.makeText(this@RegisterActivity, body.message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        // Gagal
                        Toast.makeText(this@RegisterActivity, body?.message ?: "Registration failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "Failed to register", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, t.message ?: "Error occurred", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
