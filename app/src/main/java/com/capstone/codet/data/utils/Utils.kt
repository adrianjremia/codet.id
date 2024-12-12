package com.capstone.codet.data.utils

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import com.capstone.codet.R

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val MAXIMAL_SIZE = 1000000
private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

fun createFile(application: Application): File {
    val mediasDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    val outputsDirectory = if (
        mediasDir != null && mediasDir.exists()
    ) mediasDir else application.filesDir
    return File(outputsDirectory, "$timeStamp.jpg")
}

fun createCustomTempFile(context: Context): File {
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun Uri.toFile(context: Context): File {
    val contentResolvers = context.contentResolver
    val myFiles = createCustomTempFile(context)
    val inputStream = contentResolvers.openInputStream(this) as InputStream
    val outputStream = FileOutputStream(myFiles)
    val buff = ByteArray(1024)
    var len: Int
    while (inputStream.read(buff).also { len = it } > 0) outputStream.write(buff, 0, len)
    outputStream.close()
    inputStream.close()
    return myFiles
}

fun File.toBitmap(): Bitmap {
    return BitmapFactory.decodeFile(this.path)
}


fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
    val matrix = Matrix()
    return if (isBackCamera) {
        matrix.postRotate(90f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    } else {
        matrix.postRotate(-90f)
        matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun formatDate(
    dateString: String,
    currentFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
    desiredFormat: String = "dd MMM yyyy"
): String {
    return try {
        val inputFormat = SimpleDateFormat(currentFormat, Locale.getDefault())
        val outputFormat = SimpleDateFormat(desiredFormat, Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let {
            outputFormat.format(it)
        } ?: ""
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}
