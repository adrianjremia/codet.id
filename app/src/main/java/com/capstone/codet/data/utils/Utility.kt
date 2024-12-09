package com.capstone.codet.data.utils

import android.graphics.Bitmap
import android.graphics.Matrix

fun Bitmap.rotateBitmap(isBackCamera: Boolean = false): Bitmap {
    val matrixes = Matrix()
    return if (isBackCamera) {
        matrixes.postRotate(90f)
        Bitmap.createBitmap(
            this,
            0,
            0,
            this.width,
            this.height,
            matrixes,
            true
        )
    } else {
        matrixes.postRotate(-90f)
        matrixes.postScale(-1f, 1f, this.width / 2f, this.height / 2f)
        Bitmap.createBitmap(
            this,
            0,
            0,
            this.width,
            this.height,
            matrixes,
            true
        )
    }
}
