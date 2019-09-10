package com.revolut.currencyrate.utils

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.revolut.currencyrate.model.Rate
import com.revolut.currencyrate.model.RateItem


object RateHelper {

    @JvmStatic
    fun getRateListMap(rate: Rate?): MutableMap<String, Double> {
        val map: MutableMap<String, Double> = mutableMapOf<String, Double>()

        //var map = HashMap<String,Double>()
        map.put("AUD", rate!!.aUD)
        map.put("BGN", rate!!.bGN)
        map.put("BRL", rate!!.bRL)
        map.put("CAD", rate!!.cAD)
        map.put("CHF", rate!!.cHF)
        map.put("CNY", rate!!.cNY)
        map.put("CZK", rate!!.cZK)
        map.put("DKK", rate!!.dKK)
        map.put("GBP", rate!!.gBP)
        map.put("HKD", rate!!.hKD)
        map.put("HRK", rate!!.hKD)
        map.put("HUF", rate!!.hUF)
        map.put("IDR", rate!!.iDR)
        map.put("ILS", rate!!.iLS)
        map.put("INR", rate!!.iNR)
        map.put("ISK", rate!!.iSK)
        map.put("JPY", rate!!.jPY)
        map.put("KRW", rate!!.kRW)
        map.put("MXN", rate!!.mXN)
        map.put("MYR", rate!!.mYR)
        map.put("NOK", rate!!.nOK)
        map.put("NZD", rate!!.nZD)
        map.put("PHP", rate!!.pHP)
        map.put("PLN", rate!!.pLN)
        map.put("RON", rate!!.rON)
        map.put("RUB", rate!!.rUB)
        map.put("SEK", rate!!.sEK)
        map.put("SGD", rate!!.sGD)
        map.put("THB", rate!!.tHB)
        map.put("TRY", rate!!.tRY)
        map.put("USD", rate!!.uSD)
        map.put("ZAR", rate!!.zAR)
        return map
    }

    @JvmStatic
    fun getRateList(rate: Rate?): MutableLiveData<List<RateItem>> {
        var rateLiveData = MutableLiveData<List<RateItem>>()
        var rateList = mutableListOf<RateItem>()
        rateList.add(RateItem("AUD", rate!!.aUD))
        rateList.add(RateItem("BGN", rate!!.bGN))
        rateList.add(RateItem("BRL", rate!!.bRL))
        rateList.add(RateItem("CAD", rate!!.cAD))
        rateList.add(RateItem("CHF", rate!!.cHF))
        rateList.add(RateItem("CNY", rate!!.cNY))
        rateList.add(RateItem("CZK", rate!!.cZK))
        rateList.add(RateItem("DKK", rate!!.dKK))
        rateList.add(RateItem("GBP", rate!!.gBP))
        rateList.add(RateItem("HKD", rate!!.hKD))
        rateList.add(RateItem("HRK", rate!!.hKD))
        rateList.add(RateItem("HUF", rate!!.hUF))
        rateList.add(RateItem("IDR", rate!!.iDR))
        rateList.add(RateItem("ILS", rate!!.iLS))
        rateList.add(RateItem("INR", rate!!.iNR))
        rateList.add(RateItem("ISK", rate!!.iSK))
        rateList.add(RateItem("JPY", rate!!.jPY))
        rateList.add(RateItem("KRW", rate!!.kRW))
        rateList.add(RateItem("MXN", rate!!.mXN))
        rateList.add(RateItem("MYR", rate!!.mYR))
        rateList.add(RateItem("NOK", rate!!.nOK))
        rateList.add(RateItem("NZD", rate!!.nZD))
        rateList.add(RateItem("PHP", rate!!.pHP))
        rateList.add(RateItem("PLN", rate!!.pLN))
        rateList.add(RateItem("RON", rate!!.rON))
        rateList.add(RateItem("RUB", rate!!.rUB))
        rateList.add(RateItem("SEK", rate!!.sEK))
        rateList.add(RateItem("SGD", rate!!.sGD))
        rateList.add(RateItem("THB", rate!!.tHB))
        rateList.add(RateItem("TRY", rate!!.tRY))
        rateList.add(RateItem("USD", rate!!.uSD))
        rateList.add(RateItem("ZAR", rate!!.zAR))
        rateLiveData.value = rateList

        return rateLiveData
    }

    fun hideSoftKeyboard(context: Context) {
        val inputManager: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        ///inputManager.hideSoftInputFromWindow(.windowToken, InputMethodManager.SHOW_FORCED)
    }

}

