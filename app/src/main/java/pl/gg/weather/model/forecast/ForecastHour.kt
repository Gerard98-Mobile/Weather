package pl.gg.weather.model.forecast

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForecastHour(
    @SerializedName("time_epoch")
    val timeEpoch: Long? = null,
    @SerializedName("time")
    val time: String? = null,
    @SerializedName("temp_c")
    val tempC: Double? = null,
    @SerializedName("temp_f")
    val tempF: Double? = null,
    @SerializedName("condition")
    val condition: ForecastHourCondition? = null,
) : Serializable

data class ForecastHourCondition(
    @SerializedName("icon")
    val iconUrl: String? = null
) : Serializable