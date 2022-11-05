package com.cirogg.deptepicchallenge.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cirogg.deptepicchallenge.model.FetchStatus
import com.cirogg.deptepicchallenge.model.response.DatesResponse
import com.cirogg.deptepicchallenge.repository.DatesRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatesViewModel(
    private val repo: DatesRepository
) : ViewModel() {

    private val _dates = MutableLiveData<MutableList<DatesResponse>>()
    val dates: LiveData<MutableList<DatesResponse>?> get() = _dates

    private val _datesLoad = MutableLiveData<DatesResponse>()
    val datesLoad: LiveData<DatesResponse>? get() = _datesLoad

    private val getPhotosExceptionHandler = CoroutineExceptionHandler { _, _ ->
        _datesLoad.postValue( datesLoad?.value.apply { this?.downStatus = FetchStatus.ERROR }
        )
    }

    init {
        viewModelScope.launch {
            repo.getAvailableDates().collect {
                _dates.value = it
            }
        }
    }

    fun getImagesByDate(){
        viewModelScope.launch {
            withContext(Dispatchers.IO + getPhotosExceptionHandler) {
                _dates.value.let {
                    it?.forEach { date ->
                        repo.getImagesByDate(date.date).collect { response ->
                            _datesLoad.postValue(response)
                        }
                    }
                }
            }
        }
    }

}