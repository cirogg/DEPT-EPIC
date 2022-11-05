package com.cirogg.deptepicchallenge.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cirogg.deptepicchallenge.model.response.DatesResponse
import com.cirogg.deptepicchallenge.repository.DatesRepository
import kotlinx.coroutines.launch

class DatesViewModel(
    private val repo: DatesRepository
) : ViewModel() {

    private val _dates = MutableLiveData<MutableList<DatesResponse>>()
    val dates: LiveData<MutableList<DatesResponse>?> get() = _dates


    init {
        viewModelScope.launch {
            repo.getAvailableDates().collect {
                _dates.value = it
            }
        }
    }

}