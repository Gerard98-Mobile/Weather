package pl.gg.weather.functional.cache

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import pl.gg.weather.functional.Result


abstract class CacheStrategy<T : CacheModel>{

    lateinit var cacheRepository: CacheRepository<T, *>
    abstract fun getData(query: String, time: Long? = null) : Observable<Result<T>>

    /**
     * Data is downloading from api once, and after success we using cached data till expire time
     */
    class DefaultCacheMode<T : CacheModel>: CacheStrategy<T>() {
        override fun getData(query: String, time: Long?): Observable<Result<T>> {
            return if(cacheRepository.isCachedDataAvailable(query, time)){
                cacheRepository.getCachedDataObservable(query)
                    .map { Result.Success(it) }
            } else {
                cacheRepository.loadDataFromApi(query)
                    .doOnNext {
                        if(it is Result.Success) cacheRepository.cacheData(it.data)
                    }
                    .onErrorReturn {
                        Result.Error(it)
                    }
            }
        }
    }

    /**
     * We are always trying to get data from api, but on error we check for cache
     */
    class OnApiError<T : CacheModel> : CacheStrategy<T>(){
        override fun getData(query: String, time: Long?): Observable<Result<T>> {
            return cacheRepository.loadDataFromApi(query)
                .doOnNext {
                    if(it is Result.Success) cacheRepository.cacheData(it.data)
                }
                .onErrorReturn {
                    if(cacheRepository.isCachedDataAvailable(query, time)){
                        Log.e("Cache","Loading data from cache: $query $time")
                        return@onErrorReturn Result.Success(cacheRepository.getCachedData(query))
                    } else {
                        Log.e("Cache","Cache not available for: $query $time")
                        return@onErrorReturn Result.Error(it)
                    }

                }
        }
    }

}
