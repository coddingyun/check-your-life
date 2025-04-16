package com.rali.timelane.data.di

import android.content.Context
import androidx.room.Room
import com.rali.timelane.domain.dao.ActivityDao
import com.rali.timelane.domain.dao.RoutineDao
import com.rali.timelane.domain.database.UserDatabase
import com.rali.timelane.domain.repository.ActivityRepository
import com.rali.timelane.domain.repository.RoutineRepository
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
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "user_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideActivityDao(userDatabase: UserDatabase): ActivityDao = userDatabase.activityDao()

    @Singleton
    @Provides
    fun provideActivityRepository(activityDao: ActivityDao): ActivityRepository {
        return ActivityRepository(activityDao)
    }

    @Singleton
    @Provides
    fun provideRoutineDao(userDatabase: UserDatabase): RoutineDao = userDatabase.routineDao()

    @Singleton
    @Provides
    fun provideRoutineRepository(routineDao: RoutineDao): RoutineRepository {
        return RoutineRepository(routineDao)
    }
}