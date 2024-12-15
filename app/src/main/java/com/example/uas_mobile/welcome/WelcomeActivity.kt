package com.example.uas_mobile.welcome

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.uas_mobile.LoginActivity
import com.example.uas_mobile.R
import com.example.uas_mobile.RegisterActivity
import com.example.uas_mobile.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }
    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
    private lateinit var indicatorContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupLoginButton()
        setupRegisterButton()
        setOnboardItems()
        setupIndicators()
        setCurrentIndicator(0)
    }

    private fun setupLoginButton() {
        binding.buttonLogin.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun setupRegisterButton() {
        binding.buttonRegister.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, RegisterActivity::class.java))
            finish()
        }
    }

    /**
     * Mengatur item onboarding untuk ViewPager
     */
    private fun setOnboardItems() {
        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.cashier,
                    title = "Pengalaman Terbaik Menikmati Kopi",
                    description = "Nikmati segelas kopi penuh rasa dari satu aplikasi"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.ice_coffe,
                    title = "Menikmati Kopi Kapanpun, Dimanapun",
                    description = "Bebas pilih varian kopi, bisa pick up di outlet atau dikirim langsung ke tempat kamu"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.paying,
                    title = "Berbagai Keuntungan Via Aplikasi",
                    description = "Menikmati Kopi tanpa antri, dapat promo menarik setiap hari, Coffee Poin, dan promo menarik lainnya"
                )
            )
        )

        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingItemsAdapter
        onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    /**
     * Mengatur indikator untuk ViewPager
     */
    private fun setupIndicators() {
        indicatorContainer = findViewById(R.id.indicatorContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)

        val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            setMargins(8, 0, 8, 0)
        }

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext).apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                this.layoutParams = layoutParams
            }
            indicatorContainer.addView(indicators[i])
        }
    }

    /**
     * Menentukan indikator aktif berdasarkan posisi
     */
    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorContainer.getChildAt(i) as ImageView
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    if (i == position) R.drawable.indicator_active_background
                    else R.drawable.indicator_inactive_background
                )
            )
        }
    }
}
