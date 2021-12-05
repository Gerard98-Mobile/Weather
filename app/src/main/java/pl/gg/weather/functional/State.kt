package pl.gg.weather.functional

sealed class State<T>{
    class Loading<T>(val value: Boolean = true) : State<T>()
    class Result<T>(val data: T): State<T>()
    class Error<T>(val error: Throwable?): State<T>()
}
