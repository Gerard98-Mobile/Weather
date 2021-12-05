package pl.gg.weather.model.forecast

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForecastDay(
    @SerializedName("hour")
    val hours: List<ForecastHour> = emptyList(),
    @SerializedName("time_epoch")
    val startTime: Long
) : Serializable
