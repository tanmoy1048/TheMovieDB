package com.seven.assignment.di.main

import com.google.gson.Gson
import com.seven.assignment.data.remote.ApiService
import com.seven.assignment.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class MainModule {
    @Provides
    fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideAuthRepository(
        apiService: ApiService,
        gson: Gson
    ): MainRepository {
        return MainRepository(
            apiService,
            gson
        )
    }
}