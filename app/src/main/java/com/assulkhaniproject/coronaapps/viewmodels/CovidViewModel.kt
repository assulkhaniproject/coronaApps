package com.assulkhaniproject.coronaapps.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assulkhaniproject.coronaapps.models.CommonInfo
import com.assulkhaniproject.coronaapps.models.CountryInfo
import com.assulkhaniproject.coronaapps.webservices.CovidAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CovidViewModel : ViewModel(){
    private var api = CovidAPI.instance()
    private var commonInfo = MutableLiveData<CommonInfo>()
    private var countryInfo = MutableLiveData<List<CountryInfo>>()

    fun  fetchCommonInfo(){
        api.getIndonesiaInfo().enqueue(object : Callback<CommonInfo>{
            override fun onFailure(call: Call<CommonInfo>, t: Throwable) { println(t.message) }

            override fun onResponse(call: Call<CommonInfo>, response: Response<CommonInfo>) {
                if (response.isSuccessful){
                    val i = response.body()
                    commonInfo.postValue(i)
                }
            }

        })
    }
    fun fetchCountryInfo(){
        api.getConfirmedInfo().enqueue(object : Callback<List<CountryInfo>>{
            override fun onFailure(call: Call<List<CountryInfo>>, t: Throwable) { println(t.message) }

            override fun onResponse(call: Call<List<CountryInfo>>, response: Response<List<CountryInfo>>) {
                if (response.isSuccessful){
                    val countries = response.body()
                    countryInfo.postValue(countries)
                }
            }

        })
    }
    fun listenToCommonInfo() = commonInfo
    fun listenToCountryInfo() = countryInfo

}