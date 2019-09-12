package com.revolut.currencyrate.model

import com.google.gson.annotations.SerializedName

data class Rate(

    @SerializedName("AUD")
    var aUD: Double,

    @SerializedName("BGN")
    var bGN: Double,

    @SerializedName("BRL")
    var bRL: Double,

    @SerializedName("CAD")
    var cAD: Double,

    @SerializedName("CHF")
    var cHF: Double,

    @SerializedName("CNY")
    var cNY: Double,

    @SerializedName("CZK")
    var cZK: Double,

    @SerializedName("DKK")
    var dKK: Double,

    @SerializedName("GBP")
    var gBP: Double,

    @SerializedName("HKD")
    var hKD: Double,

    @SerializedName("HRK")
    var hRK: Double,

    @SerializedName("HUF")

    var hUF: Double,
    @SerializedName("IDR")

    var iDR: Double,
    @SerializedName("ILS")

    var iLS: Double,
    @SerializedName("INR")

    var iNR: Double,
    @SerializedName("ISK")

    var iSK: Double,
    @SerializedName("JPY")

    var jPY: Double,
    @SerializedName("KRW")

    var kRW: Double,
    @SerializedName("MXN")

    var mXN: Double,
    @SerializedName("MYR")

    var mYR: Double,
    @SerializedName("NOK")

    var nOK: Double,
    @SerializedName("NZD")

    var nZD: Double,
    @SerializedName("PHP")

    var pHP: Double,
    @SerializedName("PLN")

    var pLN: Double,
    @SerializedName("RON")

    var rON: Double,
    @SerializedName("RUB")

    var rUB: Double,
    @SerializedName("SEK")

    var sEK: Double,
    @SerializedName("SGD")

    var sGD: Double,
    @SerializedName("THB")

    var tHB: Double,
    @SerializedName("TRY")

    var tRY: Double,
    @SerializedName("USD")

    var uSD: Double,

    @SerializedName("ZAR")

    var zAR: Double

)

data class RateItem(var rateKey: String, var rateValue: Float)