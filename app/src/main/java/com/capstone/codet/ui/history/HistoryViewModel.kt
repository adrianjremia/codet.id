package com.capstone.codet.ui.history

import androidx.lifecycle.ViewModel
import com.capstone.codet.data.MainRepository

class HistoryViewModel(private val repository: MainRepository):ViewModel() {

    fun history() = repository.getHistory()

}