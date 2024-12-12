package com.capstone.codet.ui.result

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.capstone.codet.R

class ResultActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_OK)
        finish()
    }
}