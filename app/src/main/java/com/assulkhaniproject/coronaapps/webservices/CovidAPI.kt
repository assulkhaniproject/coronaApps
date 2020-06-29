package com.assulkhaniproject.coronaapps.webservices

import com.assulkhaniproject.coronaapps.models.CommonInfo
import com.assulkhaniproject.coronaapps.models.CountryInfo
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

class CovidAPI{
    companion object {
        private const val ENDPOINT = "https://covid19.mathdro.id/api/"
        private var retrofit : Retrofit? = null
        private var option = OkHttpClient.Builder().apply {
            readTimeout(30,TimeUnit.SECONDS)
            writeTimeout(30,TimeUnit.SECONDS)
            connectTimeout(30,TimeUnit.SECONDS)
        }.build()

        private fun getClient() : Retrofit {
            return if (retrofit == null){
                retrofit = Retrofit.Builder().apply {
                    baseUrl(ENDPOINT)
                    client(option)
                    addConverterFactory(GsonConverterFactory.create())
                }.build()
                retrofit!!
            }else{
                retrofit!!
            }
        }
        fun instance() = getClient().create(CovidService::class.java)
    }
}
interface CovidService{
    @GET("countries/ID")
    fun getIndonesiaInfo() : Call<CommonInfo>

    @GET("confirmed")
    fun getConfirmedInfo() : Call<List<CountryInfo>>
}