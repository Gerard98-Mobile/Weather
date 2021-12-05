package pl.gg.weather.db.dao

import androidx.room.Dao
import pl.gg.weather.functional.cache.CacheDao
import pl.gg.weather.model.forecast.Forecast

@Dao
abstract class ForecastDao : CacheDao<Forecast>(Forecast::class.java.simpleName) {

}