package com.capstone.codet.data.api

import com.capstone.codet.data.response.PredictResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("/predict")
    suspend fun getPredict(
        @Part file: MultipartBody.Part
    ): PredictResponse


}