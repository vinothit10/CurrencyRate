package com.revolut.currencyrate.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.revolut.currencyrate.model.RateItem
import com.revolut.currencyrate.utils.RateHelper

class MainViewModel : ViewModel() {

    val TAG: String = "MainViewModel"
    val LOCK: Any = Object()
    val mRepository = RateRepository()
    val allRates: MutableLiveData<List<RateItem>> get() = mRepository.getMutableLiveData()

    override fun onCleared() {
        super.onCleared()
        mRepository.completableJob.cancel()
    }


    fun modifyRateListByValue() {
        Log.d(TAG," modified value "+ RateHelper.baseValue)
        val tmpRateList : MutableList<RateItem> = mutableListOf<RateItem>()
        synchronized(LOCK) {
            allRates.value?.forEach {
                tmpRateList.add(RateItem(it.rateKey, it.rateValue * RateHelper.baseValue))
            }
            allRates.postValue(tmpRateList)
        }
    }

}