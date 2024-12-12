package com.capstone.codet.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.codet.MainActivity
import com.capstone.codet.R
import com.capstone.codet.data.ViewModelFactory
import com.capstone.codet.ui.auth.LoginActivity
import com.capstone.codet.ui.setting.SettingViewModel
import com.capstone.codet.ui.start.StartActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity:AppCompatActivity() {

    private val durationTime = 3000L
    private val viewModel: SettingViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private val authViewModel: SplashViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
        checkLoginStatus()
        setupView()

    }

    private fun checkLoginStatus() {
        Handler().postDelayed({
            authViewModel.getSession().observe(this) { user ->
                if (user.isLogin) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, StartActivity::class.java))
                }
                finish()
            }
        }, durationTime)
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

}