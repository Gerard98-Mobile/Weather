package pl.gg.weather.functional.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import pl.gg.weather.util.isNetworkAvailable
import java.io.IOException

class NoConnectionInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!context.isNetworkAvailable()){
            throw NoInternetException()
        }
        return chain.proceed(chain.request())
    }
}

class NoInternetException: IOException("No internet")