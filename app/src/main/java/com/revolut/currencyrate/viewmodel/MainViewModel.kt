package com.revolut.currencyrate.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.revolut.currencyrate.model.RateItem

class MainViewModel : ViewModel() {

    val movieRepository = RateRepository()
    val allRates: LiveData<List<RateItem>> get() = movieRepository.getMutableLiveData()

    override fun onCleared() {
        super.onCleared()
        movieRepository.completableJob.cancel()
    }
}