package com.revolut.currencyrate.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.revolut.currencyrate.R
import com.revolut.currencyrate.model.RateItem
import java.util.*


class RateAdapter(val rateList: List<RateItem>?, var apiInterface: RateAdapterInterface) :
    androidx.recyclerview.widget.RecyclerView.Adapter<RateAdapter.ViewHolder>() {
    lateinit var adapterInterface: RateAdapterInterface
    var mContext: Context? = null

    override fun getItemCount() = rateList!!.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        this.mContext = parent.context
        this.adapterInterface = apiInterface

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
            holder.countryEditText?.setText(value)
        }
        holder.countryCode.setText(key)

        holder.countryEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                swapItem(position, 0)
                holder.countryEditText.showSoftInputOnFocus
            }
        }

        //holder.countryEditText.removeTextChangedListener(editedTextWatcher)
        //if (position==0){
            holder.countryEditText.addTextChangedListener(editedTextWatcher)
    }

     var editedTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(newValue: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(newValue: Editable?) {
            val rateValue: String = newValue.toString().trim()
            var value: Double
            try {
                value = rateValue.toDouble()
            } catch (e: Exception) {
                value = 0.0
            }

            rateList?.get(0)?.rateValue = value
            adapterInterface.valueModified(value)
        }
    }

    fun swapItem(fromPos: Int, toPos: Int) {
        Collections.swap(rateList, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
    }


    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val countryEditText: EditText = itemView.findViewById(R.id.rateEditText)
        val countryCode : (TextView)= itemView.findViewById(R.id.countryCode)
    }

    interface RateAdapterInterface {
        fun setServiceStatus(status: Boolean)
        fun valueModified(value: Double)
    }
}
