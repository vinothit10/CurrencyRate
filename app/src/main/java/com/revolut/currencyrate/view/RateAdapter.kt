package com.revolut.currencyrate.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.Gson
import com.jcorreia.currencyconverter.ui.RatesListComparator
import com.revolut.currencyrate.R
import com.revolut.currencyrate.model.RateItem
import com.revolut.currencyrate.utils.RateHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


open class RateAdapter(var rateList: List<RateItem>?, var apiInterface: RateAdapterInterface) :

    androidx.recyclerview.widget.RecyclerView.Adapter<RateAdapter.ViewHolder>() {
    lateinit var adapterInterface: RateAdapterInterface
    val rateQueueList: Deque<List<RateItem>> = LinkedList()
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

        holder.countryCode.setText(key)

        val flagDrawable = ResourcesCompat.getDrawable(holder.flag.resources, getFlag(key,holder.flag), null)
        holder.flag.setImageDrawable(flagDrawable)

        holder.countryEditText.isEnabled = position == 0

        holder.countryEditText.removeTextChangedListener(editedTextWatcher)
        if (position==0) {
            holder.countryEditText.addTextChangedListener(editedTextWatcher)
        }

        if(position >0) {
            holder.rateRootLayout.setOnClickListener {
                adapterInterface.currencyModified(key, value.toFloat())
            }
        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {

        val rateSet = payloads.firstOrNull() as Set<String>?
        if (rateSet==null || rateSet.isEmpty() ) {
            return super.onBindViewHolder(holder, position, payloads)
        }

        if (rateSet.contains(RatesListComparator.RATE_MODIFIED)) {
            holder.updateValue(rateList!!.get(position))
        }
    }

    var editedTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(newValue: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(newValue: Editable?) {
            val rateValue: String = newValue.toString().trim()
            var value: Float
            try {
                value = rateValue.toFloat()
            } catch (e: Exception) {
                value = 0f
            }
            rateList?.get(0)?.rateValue = value
            adapterInterface.currencyValueModified(value)
        }
    }


    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val countryEditText: EditText = itemView.findViewById(R.id.rateEditText)
        val countryCode : (TextView)= itemView.findViewById(R.id.countryCode)
        val rateRootLayout : (View) = itemView.findViewById(R.id.rate_item_root_layout)
        val flag : (ImageView) =itemView.findViewById(R.id.countryIcon)


        fun updateValue(currencyRate: RateItem) {
            val updatedRateValue: String = "%.2f".format(currencyRate.rateValue)
            if (countryEditText.text.toString() != updatedRateValue) {
                countryEditText.setText(updatedRateValue)
            }
        }
    }

    interface RateAdapterInterface {
        fun setAdapterPosition()
        fun currencyValueModified(value: Float)
        fun currencyModified(currency: String, currencyRate: Float)
    }

    fun updateRateList(newRatesList: List<RateItem>)  {
        if (rateList == null) {
            rateList = newRatesList
            notifyItemRangeInserted(0, newRatesList.size)
            return
        }
        rateQueueList.push(newRatesList)
        if (rateQueueList.size > 1) {
            return
        }

        compareRateListData(newRatesList)
    }

    fun compareRateListData(latest: List<RateItem>) {

        val ratesAdapter = this
        val result = DiffUtil.calculateDiff(RatesListComparator(rateList!!, latest))
        val isCurrencyModified: Boolean = (rateList!!.get(0).rateKey != latest!!.get(0).rateKey)
        rateList = latest
        rateQueueList.remove(latest)
        GlobalScope.launch(Dispatchers.Main) {
            result.dispatchUpdatesTo(ratesAdapter)
            if(isCurrencyModified){
                  adapterInterface.setAdapterPosition()
            }
        }
        if (rateQueueList.size > 0) {
            compareRateListData(rateQueueList.pop())
            rateQueueList.clear()
        }

    }

    fun getFlag(currencyName: String, flag: ImageView): Int {
        var currencyFlag: Int

        val resources = flag.context.resources
        val packageName = flag.context.packageName
        val drawableName = "ic_"+currencyName.substring(0,2).toLowerCase()

        currencyFlag = try {
            resources.getIdentifier(drawableName, "drawable", packageName)
        } catch (e: Exception) { 0 }

        if (currencyFlag==0)
            currencyFlag = R.drawable.ic_eu

        return currencyFlag
    }
}
