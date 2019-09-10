package com.revolut.currencyrate.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.revolut.currencyrate.R
import com.revolut.currencyrate.model.RateItem
import com.revolut.currencyrate.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),RateAdapter.ApiCallSatus {

    var mainViewModel: MainViewModel? = null
    var mRateAdapter: RateAdapter? = null
    var isSericeCallEnabled: Boolean = true


    val handler = Handler(Looper.getMainLooper())
    val runnable : Runnable = object : Runnable {
        override fun run() {
            fetchRateValues()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        swiperefresh.setOnRefreshListener { fetchRateValues() }
        startSericeCall()
    }





    fun fetchRateValues() {
        swiperefresh.setRefreshing(false)
        mainViewModel!!.allRates.observe(this, Observer { rateList ->
            prepareRecyclerView(rateList)
        })

    }

    override fun onResume() {
        //isSericeCallEnabled = true
       // RateHelper.hideSoftKeyboard()
        super.onResume()
    }



    fun prepareRecyclerView(rateList: List<RateItem>?) {

        mRateAdapter = RateAdapter(rateList, object: RateAdapter.ApiCallSatus{
            override fun setServiceStatus(isEnabled: Boolean) {
                Log.d("MainActivity", "setServiceStatus -> " + isEnabled)
                //isSericeCallEnabled = status
                if(isEnabled){
                    startSericeCall()
                }else {
                    stopSericeCall()
                }
            }
        })
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rateRecyclerView.setLayoutManager(LinearLayoutManager(this))
        } else {
            rateRecyclerView.setLayoutManager(GridLayoutManager(this, 4))
        }
        rateRecyclerView.setItemAnimator(DefaultItemAnimator())
        rateRecyclerView.setAdapter(mRateAdapter)

    }


    override fun setServiceStatus(status : Boolean) {
        isSericeCallEnabled = status
    }

    fun startSericeCall() {
        Log.d("MainActivity", "start service")
        handler.post(runnable)
    }

    fun stopSericeCall() {
        Log.d("MainActivity", "stop service")
        handler.removeCallbacks(runnable)
    }
}
