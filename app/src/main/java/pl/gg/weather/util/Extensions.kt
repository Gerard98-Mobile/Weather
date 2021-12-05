package pl.gg.weather.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun Double.toTempC() : String { return "$this C" }
fun Double.toTempF() : String { return "$this F" }

fun ImageView.load(url: String?){
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(this)
}

fun Int.str(context: Context) = context.getString(this)
inline fun <reified T> Gson.fromJson(json: String?) = fromJson<T>(json, object: TypeToken<T>() {}.type)

fun actualTime() = System.currentTimeMillis()

val Long.sToMs get() = this * 1000

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        //for other device how are able to connect with Ethernet
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        //for check internet over Bluetooth
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
        else -> false
    }
}

inline fun View.blockClick(action : () -> Unit){
    try {
        this.isClickable = false
        action()
    } finally {
        this.isClickable = true
    }
}
