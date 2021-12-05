package pl.gg.weather.core

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val disposer = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if(!disposer.isDisposed) disposer.dispose()
    }
}