package pl.gg.weather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.gg.weather.db.converter.ForecastConverter
import pl.gg.weather.db.dao.ForecastDao
import pl.gg.weather.model.forecast.Forecast

@Database(
    entities = [Forecast::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ForecastConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchDao(): ForecastDao


    companion object{
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context)
        }

        private fun buildDatabase(context: Context) =
            Room
                .databaseBuilder(context, AppDatabase::class.java, "weather.db")
                .build()
    }
}