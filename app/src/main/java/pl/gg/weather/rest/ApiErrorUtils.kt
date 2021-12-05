package pl.gg.weather.rest

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import pl.gg.weather.model.api.ErrorResponse
import retrofit2.HttpException
import pl.gg.weather.util.fromJson

object ApiErrorUtils {

    fun parseApiError(throwable: Throwable?) : String{
        return when(throwable){
            is HttpException -> {
                val json = throwable.response()?.errorBody()?.string()
                try{
                    Gson().fromJson<ErrorResponse>(json).error.message
                }catch (e: JsonSyntaxException){
                    null
                }
            }
            else -> null
        } ?: throwable?.localizedMessage ?: "Check your internet connection and try again."
    }

}