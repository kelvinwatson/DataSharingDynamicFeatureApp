package com.example.datasharingdynamicfeatureapp.di

import com.example.datasharingdynamicfeatureapp.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [MainViewModelModule::class])
interface MainActivityModule {

    @[FragmentScope ContributesAndroidInjector(modules = [MainFragmentModule::class])]
    fun contributeMainFragment(): MainFragment
}