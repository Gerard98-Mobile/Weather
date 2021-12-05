package pl.gg.weather.model.forecast

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class ForecastDays(
    @SerializedName("forecastday")
    val forecastDay: List<ForecastDay>? = null,
) : Serializable