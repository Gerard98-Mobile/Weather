package pl.gg.weather.functional.validator

import androidx.annotation.StringRes
import pl.gg.weather.R

enum class InputValidator(val regex: Regex, @StringRes val errorMessage: Int) {
    NOT_EMPTY(Regex("^\$|\\s+"), R.string.not_empty_validation_error)
}