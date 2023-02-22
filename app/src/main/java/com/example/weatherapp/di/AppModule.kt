package com.example.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [DomainModule::class, DataModule::class, PresentationModule::class])
class AppModule @Inject constructor() {

    @Singleton
    @Provides
    fun provideDomainModule(): DomainModule {
        return DomainModule()
    }

    @Singleton
    @Provides
    fun provideDataModule(): DataModule {
        return DataModule()
    }

    @Singleton
    @Provides
    fun providePresentationModule(): PresentationModule {
        return PresentationModule()
    }
}