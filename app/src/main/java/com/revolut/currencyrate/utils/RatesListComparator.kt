package com.jcorreia.currencyconverter.ui

import androidx.recyclerview.widget.DiffUtil
import com.revolut.currencyrate.model.RateItem

class RatesListComparator(private val oldRateList: List<RateItem>, private val recentRateList: List<RateItem>) : DiffUtil.Callback() {


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRateList[oldItemPosition] == recentRateList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldRate = oldRateList[oldItemPosition]
        val newRate = recentRateList[newItemPosition]

        val payloadSet = mutableSetOf<String>()

        if (oldRate.rateValue!=newRate.rateValue)
            payloadSet.add(RATE_MODIFIED)

        return payloadSet
    }

    override fun getOldListSize() = oldRateList.size

    override fun getNewListSize() = recentRateList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRateList[oldItemPosition].rateKey == recentRateList[newItemPosition].rateKey
    }

    companion object {
        const val RATE_MODIFIED = "RATE_MODIFIED"
    }
}