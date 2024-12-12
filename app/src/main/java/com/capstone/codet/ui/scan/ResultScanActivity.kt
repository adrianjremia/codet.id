package com.capstone.codet.ui.scan

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.capstone.codet.R
import com.capstone.codet.data.ViewModelFactory
import com.capstone.codet.data.utils.rotateBitmap
import com.capstone.codet.databinding.ActivityScanResultBinding
import java.io.File
import com.capstone.codet.data.utils.Result
import com.capstone.codet.data.utils.formatDate

class ResultScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanResultBinding

    private val viewModel : ScanViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imagePath = intent.getStringExtra("image_path")
        val isBackCamera = intent.getBooleanExtra("is_back_camera", true)

        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)

            val rotatedBitmap = rotateBitmap(bitmap, isBackCamera)

            binding.imgResult.setImageBitmap(rotatedBitmap)

            uploadAndPredict(File(imagePath))
        } else {
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
        }

        observePredictionResult()
    }

    private fun uploadAndPredict(file: File) {
        viewModel.uploadPrediction(file)
    }

    @SuppressLint("StringFormatInvalid")
    private fun observePredictionResult() {
        viewModel.uploadPredictionResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvNamaPenyakit.text = getString(R.string.predicting)
                    binding.tvPenyakitDesc.text = getString(R.string.predicting)
                    binding.tvIndication.text = getString(R.string.predicting)
                    binding.tvTreatment.text = getString(R.string.predicting)
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val prediction = result.data.predictResult

                    if (prediction != null) {
                        binding.apply {
                            tvNamaPenyakit.text = prediction.predict
                            tvPenyakitDesc.text = prediction.details
                            tvIndication.text = prediction.indication
                            tvTreatment.text = prediction.treatment

                            cardView.backgroundTintList = ContextCompat.getColorStateList(this@ResultScanActivity, R.color.red)
                        }

                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvNamaPenyakit.text = getString(R.string.prediction_error)
                    binding.tvPenyakitDesc.text = getString(R.string.prediction_error)
                    binding.tvIndication.text = getString(R.string.prediction_error)
                    binding.tvTreatment.text = getString(R.string.prediction_error)
                    binding.cardView.backgroundTintList = ContextCompat.getColorStateList(this@ResultScanActivity, R.color.grey)
                }
            }
        }
    }


}

