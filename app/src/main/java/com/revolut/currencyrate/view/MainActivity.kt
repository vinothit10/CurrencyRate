package com.revolut.currencyrate.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.revolut.currencyrate.R
import com.revolut.currencyrate.model.RateItem
import com.revolut.currencyrate.utils.RateHelper
import com.revolut.currencyrate.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(),RateAdapter.RateAdapterInterface {

    val TAG : String = "MainActiivty"

    var mainViewModel: MainViewModel? = null
    var mRateAdapter: RateAdapter? = null
    var isSericeCallEnabled: Boolean = true


    val handler = Handler(Looper.getMainLooper())

    var runnable : Runnable = object : Runnable {
        override fun run() {
            fetchRateValues()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        handler.post(runnable)
    }


    fun fetchRateValues() {
        mainViewModel?.allRates?.observe(this, Observer { rateList ->
            Log.d(TAG,"Rate List: allRates observed" )
            prepareRecyclerView(rateList)
            if(mRateAdapter == null) {
                prepareRecyclerView(rateList)
                Log.d(TAG,"Rate List: adapter exist" )
            }
            else {
                mRateAdapter?.updateRateList(rateList)
                Log.d(TAG,"Rate List: adapter updadte" + Gson().toJson(rateList) )
            }
        })

    }

    fun prepareRecyclerView(rateList: List<RateItem>?) {

        mRateAdapter = RateAdapter(rateList,this)
        rateRecyclerView.setLayoutManager(androidx.recyclerview.widget.LinearLayoutManager(this))
        rateRecyclerView.setItemAnimator(androidx.recyclerview.widget.DefaultItemAnimator())
        rateRecyclerView.setAdapter(mRateAdapter)

    }

    override fun setServiceStatus(status : Boolean) {
        isSericeCallEnabled = status
    }

    override fun currencyValueModified(value: Float) {
        if(RateHelper.baseValue == value){
            return
        }
        RateHelper.baseValue = value
        mainViewModel?.modifyRateListByValue()
    }

    override fun currencyModified(currency: String, currencyValue: Float) {
        if(RateHelper.baseCurrency == currency) {
            return
        }
        RateHelper.baseCurrency = currency
        RateHelper.baseValue = currencyValue
        handler.post(runnable)
    }
}
