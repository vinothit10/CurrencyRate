package com.revolut.currencyrate.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.revolut.currencyrate.R
import com.revolut.currencyrate.model.RateItem
import java.util.*


class RateAdapter(val rateList: List<RateItem>?, var apiInterface: ApiCallSatus) :
    RecyclerView.Adapter<RateAdapter.ViewHolder>() {
    lateinit var apiCallSatus: ApiCallSatus
    var mContext: Context? = null

    override fun getItemCount() = rateList!!.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        this.mContext = parent.context
        this.apiCallSatus = apiInterface

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rate_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val key = rateList!!.get(position).rateKey.toString()
        val value = rateList!!.get(position).rateValue.toString()
        if (value != null) {
            holder.tvTitle?.setText(key + ": " + value)
        }
        holder.tvTitle.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                apiCallSatus.setServiceStatus(false)
                swapItem(position, 0)
                holder.tvTitle.showSoftInputOnFocus
            } else {
                apiCallSatus.setServiceStatus(true)
            }
        }
    }

    fun swapItem(fromPos: Int, toPos: Int) {
        Collections.swap(rateList, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: EditText = itemView.findViewById(R.id.rate_value)
    }

    interface ApiCallSatus {
        fun setServiceStatus(status: Boolean)
    }
}
