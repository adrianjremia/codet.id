package com.capstone.codet.data.response

import com.google.gson.annotations.SerializedName


data class ResultResponse (
    val message: String? = null,
    val predictResult: PredictResult? = null
)


data class PredictResult (
    @SerializedName("disease_id")
    val diseaseID: Long? = null,

    val predict: String? = null,
    val details: String? = null,
    val indication: String? = null,
    val treatment: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null
)
