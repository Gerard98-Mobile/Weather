package pl.gg.weather.model.api

import com.google.gson.annotations.SerializedName
import pl.gg.weather.model.forecast.ForecastDays
import pl.gg.weather.model.forecast.Location
import java.io.Serializable

data class ForecastResponse(
    @SerializedName("location")
    val location: Location,
    @SerializedName("forecast")
    val forecastDays: ForecastDays
) : Serializable