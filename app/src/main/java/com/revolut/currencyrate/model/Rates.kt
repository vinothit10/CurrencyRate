package com.revolut.currencyrate.model

import com.google.gson.annotations.SerializedName

data class Rates(

    @SerializedName("base")
    var base: String? = null,

    @SerializedName("date")
    var date: String? = null,

    @SerializedName("rates")
    var rateList: Rate? = null


)