package com.capstone.codet.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.codet.data.MainRepository
import com.capstone.codet.data.response.PredictResponse
import com.capstone.codet.data.response.ResultResponse
import com.capstone.codet.data.utils.Result
import kotlinx.coroutines.launch
import java.io.File

class ResultViewModel(private val repository: MainRepository): ViewModel() {

    private val _uploadPredictionResult = MutableLiveData<Result<ResultResponse>>()
    val uploadPredictionResult: LiveData<Result<ResultResponse>>
        get() = _uploadPredictionResult

    fun uploadPrediction(file: File) = viewModelScope.launch {
        _uploadPredictionResult.value = Result.Loading
        try {
            repository.getResult(file).observeForever { result ->
                _uploadPredictionResult.value = result
            }
        } catch (e: Exception) {
            _uploadPredictionResult.value = Result.Error(e.localizedMessage ?: "Error uploading image")
        }
    }

}