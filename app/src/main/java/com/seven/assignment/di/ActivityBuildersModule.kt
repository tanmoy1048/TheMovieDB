package com.seven.assignment.di

import com.seven.assignment.di.main.MainBuildersModule
import com.seven.assignment.di.main.MainModule
import com.seven.assignment.di.main.MainViewModelsModule
import com.seven.assignment.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Adding up modules for the activities
 */

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = [MainViewModelsModule::class, MainModule::class, MainBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}