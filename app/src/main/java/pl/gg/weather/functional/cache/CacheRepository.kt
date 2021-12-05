package pl.gg.weather.functional.cache

import io.reactivex.rxjava3.core.Observable
import pl.gg.weather.functional.Result

abstract class CacheRepository <T : CacheModel,DAO : CacheDao<T>> constructor(
  private val dao: DAO,
  private val strategy: CacheStrategy<T>
) {

    var dataFromCache = false

    abstract fun loadDataFromApi(query: String) : Observable<Result<T>>

    fun getData(query: String, time: Long? = null) : Observable<Result<T>>{
        strategy.cacheRepository = this
        return strategy.getData(query,time)
    }

    fun cacheData(data: T){
        dao.insert(data)
    }

    fun clearCache(query: String){
        dao.clearCache(query)
    }

    fun clearCache(){
        dao.clearCache()
    }

    fun isCachedDataAvailable(query: String, time: Long? = null) : Boolean{
        return if(time != null) dao.isCachedDataAvailable(query, time) else dao.isCachedDataAvailable(query)
    }

    fun getCachedData(query: String) : T{
        dataFromCache = true
        return dao.select(query)
    }
    fun getCachedDataObservable(query: String) : Observable<T>{
        dataFromCache = true
        return Observable.just(dao.select(query))
    }

}