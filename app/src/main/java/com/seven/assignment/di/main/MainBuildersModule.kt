package com.seven.assignment.di.main

import com.seven.assignment.ui.home.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment
}