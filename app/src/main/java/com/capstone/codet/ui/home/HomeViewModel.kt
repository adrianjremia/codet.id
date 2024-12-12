package com.capstone.codet.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.codet.R
import com.capstone.codet.data.MainRepository
import com.capstone.codet.data.model.Funfact

class HomeViewModel(private val repository: MainRepository):ViewModel() {
    private val _funFacts = MutableLiveData<List<Funfact>>()
    val funFacts: LiveData<List<Funfact>> get() = _funFacts

    fun loadFunFacts(dataName: Array<String>, dataDescription: Array<String>) {
        val listTips = ArrayList<Funfact>()
        for (i in dataName.indices) {
            val fact = Funfact(dataName[i], dataDescription[i])
            listTips.add(fact)
        }
        _funFacts.value = listTips
    }

}