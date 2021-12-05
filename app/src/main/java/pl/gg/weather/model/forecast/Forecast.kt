package pl.gg.weather.model.forecast

import androidx.room.ColumnInfo
import androidx.room.Entity
import pl.gg.weather.functional.cache.CacheModel
import pl.gg.weather.model.api.ForecastResponse
import pl.gg.weather.util.sToMs
import java.io.Serializable

@Entity
data class Forecast(
    @ColumnInfo(name = "Data")
    var data: ForecastResponse? = null
) : CacheModel(), Serializable {

    constructor(key: String, data: ForecastResponse) : this(data){
        this.key = key
        this.expireTime = calculateExpireTime(data)
    }

    private fun calculateExpireTime(data: ForecastResponse) : Long{
        val lastHour = (data.forecastDays.forecastDay?.firstOrNull()?.hours?.maxOf { it.timeEpoch ?: 0L } ?: 0).sToMs
        return lastHour + 3600000L
    }

}