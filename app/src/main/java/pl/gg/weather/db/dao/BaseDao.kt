package pl.gg.weather.db.dao

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<T>)

    @Delete
    fun delete(data: T)

    @Update
    fun update(data: T)

}