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


    fun modifyRateListByValue(currentValue: Double) {
        Log.d(TAG," modified value "+currentValue)
        val tmpRateList : MutableList<RateItem> = mutableListOf<RateItem>()
        synchronized(Object()) {
            tmpRateList.add(RateItem(allRates.value?.get(0)!!.rateKey, 0.0))
            allRates.value?.forEach {
                tmpRateList.add(RateItem(it.rateKey, it.rateValue * currentValue))
            }
            Log.d(TAG, "Modified rate List: " + Gson().toJson(tmpRateList))
            allRates.postValue(tmpRateList)
        }
    }

}