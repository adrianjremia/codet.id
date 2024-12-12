package com.capstone.codet.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PredictResponse (
    val prediction: Long? = null,

    @SerializedName("file_url")
    val fileURL: String? = null,

    val message: String? = null
):Parcelable
