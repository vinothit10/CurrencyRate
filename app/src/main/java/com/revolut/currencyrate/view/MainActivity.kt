package com.revolut.currencyrate.view

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.revolut.currencyrate.R
import com.revolut.currencyrate.model.RateItem
import com.revolut.currencyrate.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener


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
        swiperefresh.setOnRefreshListener { fetchRateValues() }
        startSericeCall()

        KeyboardVisibilityEvent.setEventListener(
            this,
            object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    if(isOpen)
                        stopSericeCall()
                    else
                        startSericeCall()
                }
            })
    }


    fun fetchRateValues() {
        swiperefresh.setRefreshing(false)
        mainViewModel?.allRates?.observe(this, Observer { rateList ->
            Log.d(TAG,"Rate List: " + arrayOf(mainViewModel?.allRates))
            prepareRecyclerView(rateList)
        })

    }

    override fun onPause() {
        super.onPause()
        //RateHelper.hideSoftKeyboard(this)
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

    fun startSericeCall() {
        Log.d("MainActivity", "start service")
        handler.post(runnable)
    }

    fun stopSericeCall() {
        Log.d("MainActivity", "stop service")
        handler.removeCallbacks(runnable)
    }


    override fun valueModified(value: Double) {
        mainViewModel?.modifyRateListByValue(value)
    }
}
