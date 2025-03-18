package com.rali.checkyourlife

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // 앱 전체에서 사용 가능하게 설정
object ValidatorModule {

    @Provides
    @Singleton
    fun provideBlockDialogValidator(): BlockDialogValidator {
        return BlockDialogValidator()
    }
}
