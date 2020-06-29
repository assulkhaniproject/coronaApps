package com.assulkhaniproject.coronaapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Align
import com.anychart.enums.HoverMode
import com.anychart.enums.LegendLayout
import com.anychart.enums.TooltipPositionMode
import com.assulkhaniproject.coronaapps.models.CommonInfo
import com.assulkhaniproject.coronaapps.models.CountryInfo
import com.assulkhaniproject.coronaapps.viewmodels.CovidViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.chart_bar.*
import kotlinx.android.synthetic.main.chart_pie.*

class MainActivity : AppCompatActivity() {

    private lateinit var covidViewModel: CovidViewModel
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.btn_web)
        button.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }

        covidViewModel = ViewModelProvider(this).get(CovidViewModel::class.java)
        covidViewModel.fetchCommonInfo()
        covidViewModel.fetchCountryInfo()
        covidViewModel.listenToCommonInfo().observe(this, Observer {
            attachCommonInfo(it)
        })
        covidViewModel.listenToCountryInfo().observe(this, Observer {
            if (it.size > 5){
                attachCountryInfo(it.take(5))
            }
        })
    }
    private fun attachCommonInfo(it: CommonInfo){
        pie_chart.setProgressBar(progress_chart)
        APIlib.getInstance().setActiveAnyChartView(pie_chart)
        val pie = AnyChart.pie()
        val data : MutableList<DataEntry> = mutableListOf()
        data.add(ValueDataEntry("Positif", it.confirmed.value))
        data.add(ValueDataEntry("Sembuh", it.recovered.value))
        data.add(ValueDataEntry("Meninggal", it.deaths.value))
        pie.data(data)

        pie.title("Kasus Covid-19 Di Indonesia")
        pie.labels().position("outside")
        pie.legend().title().enabled(true)
        pie.legend().position("center-bottom").itemsLayout(LegendLayout.HORIZONTAL).align(Align.CENTER)
        pie_chart.setChart(pie)
    }
    private fun attachCountryInfo(it : List<CountryInfo>){
        bar_chart.setProgressBar(progress_bar)
        APIlib.getInstance().setActiveAnyChartView(bar_chart)

        val cartesian = AnyChart.cartesian()
        val data : MutableList<DataEntry> = mutableListOf()
        for(x in it){
            val name = if (x.countryName.length > 5) x.countryName.substring(0,4)+ ".." else x.countryName
            data.add(ValueDataEntry(name, x.confirmed))
        }
        cartesian.column(data)
        cartesian.animation(true)
        cartesian.title("Covid-19 Di 5 Negara")
        cartesian.yScale().minimum(0.0)
        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }")
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)


        cartesian.xAxis(0).title("Negara")
        cartesian.yAxis(0).title("Positif")
        bar_chart.setChart(cartesian)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


}
