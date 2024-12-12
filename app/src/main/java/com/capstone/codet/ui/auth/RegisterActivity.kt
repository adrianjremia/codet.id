package com.capstone.codet.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.codet.data.ViewModelFactory
import com.capstone.codet.data.utils.Result
import com.capstone.codet.databinding.ActivityRegisterBinding
import com.capstone.codet.ui.setting.SettingViewModel

class RegisterActivity:AppCompatActivity() {

    private lateinit var binding:ActivityRegisterBinding

    private val authViewModel: RegisterViewModel by viewModels{
        ViewModelFactory.getInstance(this)
    }

    private val viewModel: SettingViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }


        binding.btnSignUp.setOnClickListener {
            register()
        }

        binding.linearSignUp.setOnClickListener {
            navigateToLogin()
        }

    }

    private fun register(){
        val name = binding.edtUsername.text.toString()
        val email = binding.edtEmailRegister.text.toString()
        val password = binding.edtPasswordRegister.text.toString()

        if (validateInputs(name, email, password )) {
            authViewModel.register(name, email, password)
                .observe(this@RegisterActivity) { result ->
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showLoading(false)
                            Toast.makeText(
                                this@RegisterActivity,
                                "Pendaftaran berhasil",
                                Toast.LENGTH_SHORT
                            ).show()
                            navigateToLogin()
                        }

                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(
                                this@RegisterActivity,
                                result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun validateInputs(
        name: String,
        email: String,
        password: String,

    ): Boolean {
        return when {
            name.isEmpty() || email.isEmpty() || password.isEmpty()  -> {
                Toast.makeText(this, "Silakan isi semua form", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}