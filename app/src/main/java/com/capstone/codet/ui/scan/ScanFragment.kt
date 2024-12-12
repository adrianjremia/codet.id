package com.capstone.codet.ui.scan

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.capstone.codet.data.utils.rotateBitmap
import com.capstone.codet.data.utils.uriToFile
import com.capstone.codet.databinding.FragmentScanBinding
import com.capstone.codet.ui.result.ResultActivity
import java.io.File

class ScanFragment:Fragment() {

    private lateinit var binding: FragmentScanBinding

    private var getFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        binding.buttonCamera.setOnClickListener { startCameraX() }
        binding.buttonGallery.setOnClickListener { startGallery() }
        binding.buttonUpload.setOnClickListener { getResult() }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun startCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun getResult() {
        if (getFile != null) {
            val intent = Intent(requireContext(), ResultScanActivity::class.java)
            intent.putExtra("image_path", getFile!!.absolutePath)
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("RestrictedApi")
    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == CAMERA_X_RESULT) {
            val myFile = result.data?.getSerializableExtra("picture") as File
            val isBackCamera = result.data?.getBooleanExtra("isBackCamera", true) ?: true

            getFile = myFile

            val resultBitmap = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera
            )
            binding.previewImage.setImageBitmap(resultBitmap)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())

            getFile = myFile
            binding.previewImage.setImageURI(selectedImg)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }


}