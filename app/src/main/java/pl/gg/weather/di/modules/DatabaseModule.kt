package pl.gg.weather.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.gg.weather.db.AppDatabase
import pl.gg.weather.db.dao.ForecastDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.invoke(appContext)
    }

    @Provides
    @Singleton
    fun provideSearchDao(appDatabase: AppDatabase) : ForecastDao {
        return appDatabase.searchDao()
    }
}