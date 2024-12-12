package com.capstone.codet.data.api.main


import com.capstone.codet.data.response.HistoryResponse
import com.capstone.codet.data.response.LoginResponse
import com.capstone.codet.data.response.RegisterResponse
import com.capstone.codet.data.response.ResultResponse
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("auth/login")
    suspend fun logins(@Body request: LoginRequest): LoginResponse

    @Multipart
    @POST("trees/result")
    suspend fun getResult(
        @Part file: MultipartBody.Part
    ): ResultResponse


    @GET("trees/history")
    suspend fun getHistory(): HistoryResponse

}


data class RegisterRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)