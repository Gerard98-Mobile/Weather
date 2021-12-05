package pl.gg.weather.rest

import io.reactivex.rxjava3.core.Observable
import pl.gg.weather.model.api.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    @GET("v1/forecast.json")
    fun getWeather(
        @Query("q") query: String,
        @Query("key") key: String = ApiKeyStorage.apiKey
    ): Observable<ForecastResponse>
}
