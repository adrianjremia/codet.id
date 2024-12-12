package com.capstone.codet.ui.start

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.codet.data.ViewModelFactory
import com.capstone.codet.databinding.ActivityStartBinding
import com.capstone.codet.ui.auth.LoginActivity
import com.capstone.codet.ui.auth.RegisterActivity
import com.capstone.codet.ui.setting.SettingViewModel

class StartActivity:AppCompatActivity() {

    private lateinit var binding:ActivityStartBinding

    private val viewModel: SettingViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        binding.apply {

            btnLoginStart.setOnClickListener {
                val intent = Intent(this@StartActivity, LoginActivity::class.java)
                startActivity(intent)
            }

            btnRegisterStart.setOnClickListener {
                val intent = Intent(this@StartActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

        }

    }


}