package pl.gg.weather

sealed class IPreset(
    val api: String
)

class Dev : IPreset(
    api = "https://api.weatherapi.com/",
)