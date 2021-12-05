package pl.gg.weather.db.repository

import io.reactivex.rxjava3.core.Observable
import pl.gg.weather.db.dao.ForecastDao
import pl.gg.weather.functional.cache.CacheRepository
import pl.gg.weather.functional.Result
import pl.gg.weather.functional.cache.CacheStrategy
import pl.gg.weather.model.forecast.Forecast
import pl.gg.weather.rest.ForecastApi
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private var forecastApi: ForecastApi,
    private var forecastDao: ForecastDao
) : CacheRepository<Forecast, ForecastDao>(forecastDao, CacheStrategy.DefaultCacheMode()) {

    override fun loadDataFromApi(query: String): Observable<Result<Forecast>> =
        forecastApi.getWeather(query)
            .map { Result.Success(Forecast(query, it)) }

}