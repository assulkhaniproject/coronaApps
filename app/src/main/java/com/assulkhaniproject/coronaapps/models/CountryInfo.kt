package com.assulkhaniproject.coronaapps.models

import com.google.gson.annotations.SerializedName

data class CountryInfo(
    @SerializedName ("countryRegion") var countryName: String = "",
    @SerializedName ("confirmed") var confirmed : Int = 0
)