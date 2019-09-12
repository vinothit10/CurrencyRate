package com.revolut.currencyrate.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.revolut.currencyrate.model.RateItem
import com.revolut.currencyrate.utils.RateHelper
import com.revolut.currencyrate.utils.RateHelper.LOCK

class MainViewModel : ViewModel() {

    val TAG: String = "MainViewModel"

    val mRepository = RateRepository()
    val allRates: MutableLiveData<List<RateItem>> get() = mRepository.getMutableLiveData()

    override fun onCleared() {
        super.onCleared()
        mRepository.completableJob.cancel()
    }


    fun modifyRateListByValue(updatedRate: Float) {
        if(RateHelper.baseValue == updatedRate){
            return
        }

        Log.d(TAG," data modified value "+ RateHelper.baseValue)
        val tmpRateList : MutableList<RateItem> = mutableListOf<RateItem>()
        synchronized(LOCK) {
            RateHelper.baseValue = updatedRate
            allRates.value?.forEach {
                tmpRateList.add(RateItem(it.rateKey, it.rateValue * RateHelper.baseValue))
            }
            allRates.postValue(tmpRateList)
        }
    }

}