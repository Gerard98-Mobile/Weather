package pl.gg.weather.ui.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.gg.weather.core.BaseViewModel
import pl.gg.weather.db.repository.ForecastRepository
import pl.gg.weather.model.forecast.Forecast
import pl.gg.weather.functional.State
import javax.inject.Inject
import pl.gg.weather.functional.Result
import pl.gg.weather.util.actualTime


@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val savedState: SavedStateHandle
) : BaseViewModel() {

    val state = MutableLiveData<State<Forecast>>()
    val querySavedState = savedState.getLiveData<String>("query")

    fun loadWeather(){
        val query = querySavedState.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.Loading(true))
            forecastRepository.getData(query, actualTime())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    when (it) {
                        is Result.Success -> { state.postValue(State.Result(it.data)) }
                        is Result.Error -> state.postValue(State.Error(it.throwable))
                    }
                }
                .addTo(disposer)
        }
    }

    fun isDataFromCache(): Boolean = forecastRepository.dataFromCache

}