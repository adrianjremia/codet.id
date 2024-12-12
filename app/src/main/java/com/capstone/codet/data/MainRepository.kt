package com.capstone.codet.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.codet.data.api.main.ApiConfig
import com.capstone.codet.data.api.ml.MLApiService
import com.capstone.codet.data.api.main.ApiService
import com.capstone.codet.data.api.main.LoginRequest
import com.capstone.codet.data.api.main.RegisterRequest
import com.capstone.codet.data.model.User
import com.capstone.codet.data.preference.SettingPreference
import com.capstone.codet.data.response.ErrorResponse
import com.capstone.codet.data.response.ListHistory
import com.capstone.codet.data.response.LoginResponse
import com.capstone.codet.data.response.PredictResponse
import com.capstone.codet.data.response.RegisterResponse
import com.capstone.codet.data.response.ResultResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import com.capstone.codet.data.utils.Result
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


class MainRepository(
    private val mlApiService: MLApiService,
    private val preference: SettingPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: User) {
        preference.saveSession(user)
    }

    fun getSession(): Flow<User> {
        return preference.getSession()
    }

    suspend fun logout() {
        preference.logout()
    }

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val request = RegisterRequest(name, email, password)
            val response = apiService.register(request)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            Log.d("register", e.message.toString())
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun login(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val request = LoginRequest(email, password)
            val response = apiService.logins(request)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            Log.d("login", e.message.toString())
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }

    }

    fun getHistory(): LiveData<Result<List<ListHistory>>> = liveData {
        emit(Result.Loading)
        try {
            val token = preference.getSession().first().token
            val api = ApiConfig.getApiService(token)
            val response = api.getHistory()

            // Extract the list of stories
            val message = response.message
            if (message != null) {
                emit(Result.Success(message))
            } else {
                emit(Result.Error("Error fetching stories"))
            }
        } catch (e: HttpException) {
            Log.d("StoryRepository", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun getResult(
        imageFile: File,
    ): LiveData<Result<ResultResponse>> = liveData {
        emit(Result.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )

        try {
            val token = preference.getSession().first().token
            val api = ApiConfig.getApiService(token)
            // Attempt to fetch prediction
            val successResponse = api.getResult(multipartBody)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            // Handle HTTP error responses
            val errorBody = e.response()?.errorBody()?.string()
            if (!errorBody.isNullOrEmpty()) {
                try {
                    val errorResponse = Gson().fromJson(errorBody, ResultResponse::class.java)
                    emit(Result.Error(errorResponse.message ?: "Unknown error occurred"))
                } catch (jsonException: JsonSyntaxException) {
                    emit(Result.Error("Server error: ${e.message()}"))
                }
            } else {
                emit(Result.Error("Unknown server error"))
            }
        } catch (e: Exception) {
            // Handle other exceptions
            emit(Result.Error("Unexpected error: ${e.localizedMessage ?: "Error occurred"}"))
        }
    }



    suspend fun getPrediction(
        imageFile: File,
    ): LiveData<Result<PredictResponse>> = liveData {
        emit(Result.Loading)
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )

        try {
            // Attempt to fetch prediction
            val successResponse = mlApiService.getPredict(multipartBody)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            // Handle HTTP error responses
            val errorBody = e.response()?.errorBody()?.string()
            if (!errorBody.isNullOrEmpty()) {
                try {
                    val errorResponse = Gson().fromJson(errorBody, PredictResponse::class.java)
                    emit(Result.Error(errorResponse.fileURL ?: "Unknown error occurred"))
                } catch (jsonException: JsonSyntaxException) {
                    emit(Result.Error("Server error: ${e.message()}"))
                }
            } else {
                emit(Result.Error("Unknown server error"))
            }
        } catch (e: Exception) {
            // Handle other exceptions
            emit(Result.Error("Unexpected error: ${e.localizedMessage ?: "Error occurred"}"))
        }
    }


    //preference theme
    fun getThemeSetting(): Flow<Boolean> {
        return preference.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        preference.saveThemeSetting(isDarkModeActive)
    }


    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance(mlApiService: MLApiService, preference: SettingPreference, apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: MainRepository(mlApiService, preference, apiService)
            }.also { instance = it }
    }

}