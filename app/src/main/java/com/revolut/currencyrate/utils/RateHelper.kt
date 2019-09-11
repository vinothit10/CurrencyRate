package com.revolut.currencyrate.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData
import com.revolut.currencyrate.model.RateItem
import com.revolut.currencyrate.model.Rates


object RateHelper {

    var baseCurrency: String = "EUR"
    var baseValue: Double = 1.0

    @JvmStatic
    fun getRateList(rates: Rates) :  MutableLiveData<List<RateItem>> {
        var rateLiveData = MutableLiveData<List<RateItem>>()
        synchronized(Object()) {
            var rateList = mutableListOf<RateItem>()
            rates.rateList?.forEach {
                rateList.add(RateItem(it.key, it.value * baseValue))
            }
            rateLiveData.value = rateList
        }
        return rateLiveData
    }


    fun hideSoftKeyboard(context: Context,view: View) {
        val inputManager:InputMethodManager =context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.SHOW_FORCED)
    }

}

