package com.revolut.currencyrate.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Rates(

    @SerializedName("base")
    @Expose
    var base: String? = null,

    @SerializedName("date")
    @Expose
    var date: String? = null,

    @SerializedName("rates")
    @Expose
    val rateList: Map<String, Float>? = null

)