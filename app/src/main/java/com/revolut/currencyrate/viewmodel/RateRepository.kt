package com.revolut.currencyrate.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.revolut.currencyrate.model.RateItem
import com.revolut.currencyrate.networking.RestApiService
import com.revolut.currencyrate.utils.RateHelper
import kotlinx.coroutines.*
import retrofit2.HttpException

class RateRepository {

    val TAG: String? = "RateRepository"

    var rateLiveData = MutableLiveData<List<RateItem>>()
    val completableJob = Job()
    val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    val thisApiCorService by lazy {
        RestApiService.createCurrencyService()
    }

    fun getMutableLiveData(): MutableLiveData<List<RateItem>> {
        coroutineScope.launch {
            val request = thisApiCorService.getRates("latest", "EUR")
            withContext(Dispatchers.Main) {
                try {

                    val response = request.await()
                    val rateResponse = response
                    rateLiveData = RateHelper.getRateList(rateResponse?.rateList)

                } catch (e: HttpException) {
                    Log.e(TAG, "Error while fetching api response", e)

                } catch (e: Throwable) {
                    Log.e(TAG, "Error while fetching api response", e)
                }
            }
        }
        return rateLiveData
    }
}