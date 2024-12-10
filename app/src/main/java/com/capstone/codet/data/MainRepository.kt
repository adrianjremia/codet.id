package com.capstone.codet.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.codet.data.api.ApiService
import com.capstone.codet.data.preference.SettingPreference
import com.capstone.codet.data.response.ErrorResponse
import com.capstone.codet.data.response.PredictResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import com.capstone.codet.data.utils.Result
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow

class MainRepository(
    private val apiService: ApiService,
    private val preference: SettingPreference
) {


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
            val successResponse = apiService.getPredict(multipartBody)
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

        fun getInstance(apiService: ApiService,preference: SettingPreference,) =
            instance ?: synchronized(this) {
                instance ?: MainRepository(apiService, preference,)
            }.also { instance = it }
    }

}