package com.revolut.currencyrate.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolut.currencyrate.model.RateItem

class MainViewModel : ViewModel() {

    val TAG: String = "MainViewModel"

    val movieRepository = RateRepository()
    val allRates: MutableLiveData<List<RateItem>> get() = movieRepository.getMutableLiveData()

    override fun onCleared() {
        super.onCleared()
        movieRepository.completableJob.cancel()
    }

    fun modifyRateListByValue(currentValue: Double) {
        Log.d(TAG," modified value "+currentValue)
        val tmpRateList : MutableList<RateItem> = mutableListOf<RateItem>()
        allRates.value?.forEach {
            tmpRateList.add(RateItem(it.rateKey, it.rateValue * currentValue))
        }
        allRates.value = tmpRateList
    }

}