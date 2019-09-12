package com.revolut.currencyrate.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.revolut.currencyrate.model.RateItem
import com.revolut.currencyrate.model.Rates


object RateHelper {

    val TAG : String = "RateHelper"
    var baseCurrency: String? = "EUR"
    var baseValue: Float = 1.0f
    val LOCK: Any = Object()

    @JvmStatic
    fun getRateList(rates: Rates) :  MutableLiveData<List<RateItem>> {
        Log.d(TAG," data modified value (entering lock) "+ baseValue)
        Log.d(TAG,"baseCurrency,baseValue, list "+ baseCurrency + " , " + baseValue + " , " + Gson().toJson(rates) )

        synchronized(LOCK) {
            var rateLiveData = MutableLiveData<List<RateItem>>()
            Log.d(TAG," data modified value (released lock) "+ baseValue)

            baseCurrency = rates.base
            var rateList = mutableListOf<RateItem>()
            rateList.add(RateItem(baseCurrency!!, baseValue))
            rates.rateList?.forEach {
                rateList.add(RateItem(it.key, it.value * baseValue))
                //Log.d(TAG,"item value, baseValue,sum "+ it.value + " , " + baseValue+ " , " +(it.value * baseValue) )
            }
            rateLiveData.value = rateList
            return rateLiveData
        }

    }


    fun hideSoftKeyboard(context: Context,view: View) {
        val inputManager:InputMethodManager =context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.SHOW_FORCED)
    }


}

