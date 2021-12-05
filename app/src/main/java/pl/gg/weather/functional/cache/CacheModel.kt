package pl.gg.weather.functional.cache

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import io.reactivex.rxjava3.disposables.Disposable

open class CacheModel{
    @PrimaryKey
    @ColumnInfo(name = "Key")
    var key: String = ""
    @ColumnInfo(name = "ExpireTime")
    var expireTime: Long? = null
}