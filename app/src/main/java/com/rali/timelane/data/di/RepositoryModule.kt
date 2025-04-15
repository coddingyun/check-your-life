package com.rali.timelane.data.di

import android.content.Context
import androidx.room.Room
import com.rali.timelane.domain.dao.ActivityDao
import com.rali.timelane.domain.database.ActivityDatabase
import com.rali.timelane.domain.repository.ActivityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ActivityDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ActivityDatabase::class.java,
            "activity_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideActivityDao(activityDatabase: ActivityDatabase): ActivityDao = activityDatabase.activityDao()

    @Singleton
    @Provides
    fun provideActivityRepository(activityDao: ActivityDao): ActivityRepository {
        return ActivityRepository(activityDao)
    }
}