package com.example.datasharingdynamicfeatureapp.di

import androidx.lifecycle.ViewModel
import com.example.datasharingdynamicfeatureapp.MainFragmentStringProvider
import com.example.datasharingdynamicfeatureapp.MainFragmentStringProviderImpl
import com.example.datasharingdynamicfeatureapp.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainViewModelModule {

    @Binds
    fun bindMainFragmentHelper(instance: MainFragmentStringProviderImpl): MainFragmentStringProvider

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(instance: MainViewModel): ViewModel
}