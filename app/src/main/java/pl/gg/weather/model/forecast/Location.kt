package pl.gg.weather.model.forecast

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location(
    @SerializedName("lat")
    val latitude: Double? = null,
    @SerializedName("lon")
    val longitude: Double? = null
) : Serializable