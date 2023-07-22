package com.example.datasharingdynamicfeatureapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ViewModelModule::class
    ]
)
interface AppGraph : AndroidInjector<DataSharingDynamicFeatureApp> {

    override fun inject(application: DataSharingDynamicFeatureApp)

    fun getAppContext(): Context

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun withAppContext(appContext: Context): Builder

        fun build(): AppGraph
    }
}