package pl.gg.weather.functional.cache

import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import pl.gg.weather.db.dao.BaseDao
import pl.gg.weather.model.forecast.Forecast

abstract class CacheDao<T : CacheModel>(
    private val tableName: String
) : BaseDao<T> {

    @RawQuery(observedEntities = [Forecast::class])
    protected abstract fun select(query: SimpleSQLiteQuery) : T
    fun select(key: String) : T {
        val rawQuery = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE Key = '$key'")
        return select(rawQuery)
    }

    @RawQuery
    protected abstract fun clearCache(query: SimpleSQLiteQuery) : Int
    fun clearCache(key: String){
        val rawQuery = SimpleSQLiteQuery("DELETE FROM $tableName WHERE Key = '$key'")
        clearCache(rawQuery)
    }
    fun clearCache(){
        val rawQuery = SimpleSQLiteQuery("DELETE FROM $tableName")
        clearCache(rawQuery)
    }

    @RawQuery
    protected abstract fun isCachedDataAvailable(query: SimpleSQLiteQuery) : Int
    fun isCachedDataAvailable(key: String, time: Long) : Boolean{
        val rawQuery = SimpleSQLiteQuery("SELECT COUNT(*) FROM $tableName WHERE Key = '$key' AND ExpireTime > $time")
        return isCachedDataAvailable(rawQuery) > 0
    }
    fun isCachedDataAvailable(key: String) : Boolean{
        val rawQuery = SimpleSQLiteQuery("SELECT COUNT(*) FROM $tableName WHERE Key = '$key'")
        return isCachedDataAvailable(rawQuery) > 0
    }

}