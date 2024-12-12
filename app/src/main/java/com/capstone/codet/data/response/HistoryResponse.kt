package com.capstone.codet.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class HistoryResponse (
    val message: List<ListHistory>? = null
)


@Parcelize
data class ListHistory (
    @SerializedName("scan_id")
    val scanID: Long? = null,

    val status: String? = null,
    val details: String? = null,
    val indication: String? = null,
    val treatment: String? = null,

    @SerializedName("image_url")
    val imageURL: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("user_id")
    val userID: Long? = null,

    @SerializedName("disease_id")
    val diseaseID: Long? = null
):Parcelable
