package pl.gg.weather.model.api

import com.google.gson.annotations.SerializedName

class ErrorResponse(
    @SerializedName("error")
    val error: ErrorBody
)

class ErrorBody(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("message")
    val message: String? = null
)