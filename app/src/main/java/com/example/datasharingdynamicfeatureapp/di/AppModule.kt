package com.example.datasharingdynamicfeatureapp.di

import com.example.datasharingdynamicfeatureapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AppModule {
    @[ActivityScope ContributesAndroidInjector(modules = [MainActivityModule::class])]
    fun contributeMainActivity(): MainActivity
}