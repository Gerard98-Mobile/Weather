package pl.gg.weather.ui.location

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.gg.weather.core.BaseViewModel
import pl.gg.weather.db.repository.ForecastRepository
import javax.inject.Inject

@HiltViewModel
class PassLocationViewModel @Inject constructor(private val forecastRepository: ForecastRepository) : BaseViewModel() {

    fun clearCache(){
        viewModelScope.launch(Dispatchers.IO) {
            forecastRepository.clearCache()
        }
    }

}