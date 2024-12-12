package com.capstone.codet.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.codet.MainActivity
import com.capstone.codet.data.ViewModelFactory
import com.capstone.codet.data.model.User
import com.capstone.codet.databinding.ActivityLoginBinding
import com.capstone.codet.ui.setting.SettingViewModel
import com.capstone.codet.data.utils.Result

class LoginActivity:AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val authViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private val viewModel: SettingViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }


        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.linearLogin.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun login() {
        binding.apply {
            val email = edtEmailLogin.text.toString()
            val password = edtPasswordLogin.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.login(email, password).observe(this@LoginActivity) { result ->
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            val token = result.data.token
                            if (token != null) {
                                authComplete(token)
                            }
                        }
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(
                                this@LoginActivity,
                                result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun authComplete(token: String) {
        val email = binding.edtEmailLogin.text.toString()
        authViewModel.saveSession(User(email, token))
        goToMainActivity()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }




}

