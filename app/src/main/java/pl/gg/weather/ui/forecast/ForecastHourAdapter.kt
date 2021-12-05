package pl.gg.weather.ui.forecast

import android.content.Context
import pl.gg.weather.core.MultiRecyclerAdapter
import pl.gg.weather.core.VH
import pl.gg.weather.databinding.ViewForecastHourBinding
import pl.gg.weather.databinding.ViewTextBinding
import pl.gg.weather.model.forecast.ForecastHour
import pl.gg.weather.util.load
import pl.gg.weather.util.toTempC
import pl.gg.weather.util.toTempF

class ForecastHourAdapter(context: Context, data: List<Any>) : MultiRecyclerAdapter<Any>(context) {

    init{
        register({it is ForecastHour}, ::bindForecast, ViewForecastHourBinding::inflate)
        register({it is String}, ::bind, ViewTextBinding::inflate)
        this.data = data
    }

    private fun bindForecast(vh: VH<ViewForecastHourBinding>, item: ForecastHour) = vh.binding.run {
        tempC.text = item.tempC?.toTempC()
        tempF.text = item.tempF?.toTempF()
        conditionImg.load(item.condition?.iconUrl?.fixUrl())
        time.text = item.time
    }

    private fun bind(vh: VH<ViewTextBinding>, item: String) = vh.binding.run {
        vh.binding.text.text = item
    }

    private fun String.fixUrl() = if(this.length > 1 && this.substring(0,2) == "//") "https://${this.substring(2,this.length)}" else this
}