package com.example.checkyourlife

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideActivityRepository(activityDao: ActivityDao): ActivityRepository {
        return ActivityRepository(activityDao)
    }
}