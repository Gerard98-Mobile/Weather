package pl.gg.weather.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.gg.weather.model.api.ForecastResponse
import pl.gg.weather.util.fromJson

class ForecastConverter {
    private val gson = Gson()
    private val type = object : TypeToken<ForecastResponse>() {}.type

    @TypeConverter
    fun mapObjectToJson(value: ForecastResponse): String {
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun mapJsonToObject(value: String?): ForecastResponse {
        return gson.fromJson<ForecastResponse>(value)
    }
}