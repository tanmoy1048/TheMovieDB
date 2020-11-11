package com.seven.assignment.di

import androidx.lifecycle.ViewModelProvider
import com.seven.assignment.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

/**
 * Module to inject viewmodel factory
 */

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}