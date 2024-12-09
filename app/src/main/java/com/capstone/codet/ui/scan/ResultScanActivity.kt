package com.capstone.codet.ui.scan

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.codet.data.utils.rotateBitmap
import com.capstone.codet.databinding.ActivityResultBinding
import com.capstone.codet.databinding.ActivityScanResultBinding

class ResultScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the image path and isBackCamera flag from the Intent
        val imagePath = intent.getStringExtra("image_path")
        val isBackCamera = intent.getBooleanExtra("is_back_camera", true)

        if (imagePath != null) {
            // Decode the image file into a bitmap
            val bitmap = BitmapFactory.decodeFile(imagePath)

            // Rotate the bitmap using the utility function
            val rotatedBitmap = rotateBitmap(bitmap, isBackCamera)

            // Set the rotated image to imgResult
            binding.imgResult.setImageBitmap(rotatedBitmap)
        } else {
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
        }
    }
}

