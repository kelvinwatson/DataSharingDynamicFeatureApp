package com.example.datasharingdynamicfeatureapp.di

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class DataSharingDynamicFeatureApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppGraph.builder().withAppContext(this).build()
}