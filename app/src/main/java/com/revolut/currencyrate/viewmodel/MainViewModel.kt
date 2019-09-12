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

    val mRepository = RateRepository()
    val allRates: MutableLiveData<List<RateItem>> get() = mRepository.getMutableLiveData()

    override fun onCleared() {
        super.onCleared()
        mRepository.completableJob.cancel()
    }


    fun modifyRateListByValue() {
        Log.d(TAG," modified value "+ RateHelper.baseValue)
        val tmpRateList : MutableList<RateItem> = mutableListOf<RateItem>()
        synchronized(Object()) {
            allRates.value?.forEach {
                tmpRateList.add(RateItem(it.rateKey, it.rateValue * RateHelper.baseValue))
            }
            Log.d(TAG, "Modified rate List: " + Gson().toJson(tmpRateList))
            allRates.postValue(tmpRateList)
        }
    }

}