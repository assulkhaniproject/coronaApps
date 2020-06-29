package com.assulkhaniproject.coronaapps.models

import com.google.gson.annotations.SerializedName


data class CommonInfo(
        @SerializedName("confirmed") var confirmed : DetailCommonInfo,
        @SerializedName("recovered") var recovered : DetailCommonInfo,
        @SerializedName("deaths") var deaths : DetailCommonInfo,
        @SerializedName("lastUpdate") var lastUpdate : String = ""
    )

data class DetailCommonInfo(
        @SerializedName("value") var value : Int = 0,
        @SerializedName("detail") var detail : String = ""
)