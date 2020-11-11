package com.seven.assignment.di.main

import androidx.lifecycle.ViewModel
import com.seven.assignment.di.ViewModelKey
import com.seven.assignment.ui.home.MainFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    abstract fun bindMainFragmentViewModel(fragmentViewModel: MainFragmentViewModel): ViewModel
}