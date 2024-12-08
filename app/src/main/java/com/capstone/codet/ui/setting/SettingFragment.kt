package com.capstone.codet.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.codet.data.ViewModelFactory
import com.capstone.codet.databinding.FragmentSettingBinding
import com.capstone.codet.ui.about.AboutActivity

class SettingFragment:Fragment() {

    private lateinit var binding: FragmentSettingBinding

    private val viewModel: SettingViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive ->
            binding.switchTheme.setOnCheckedChangeListener(null) // Remove listener temporarily
            binding.switchTheme.isChecked = isDarkModeActive
            binding.switchTheme.setOnCheckedChangeListener(themeSwitchListener) // Reattach listener
        }

        // Set up listener for switchTheme to save theme setting
        binding.switchTheme.setOnCheckedChangeListener(themeSwitchListener)

        binding.tvAboutUs.setOnClickListener {
            var intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }


    }

    // Simplified theme switch listener: Only save the setting
    private val themeSwitchListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        viewModel.saveThemeSetting(isChecked)
    }


}