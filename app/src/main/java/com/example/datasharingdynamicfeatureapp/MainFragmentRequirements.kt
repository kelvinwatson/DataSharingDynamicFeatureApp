package com.example.datasharingdynamicfeatureapp

import android.content.Context
import javax.inject.Inject

class MainFragmentStringProviderImpl @Inject constructor(appContext: Context) :
    MainFragmentStringProvider {

    private val resources by lazy {
        appContext.resources
    }

    override fun getAppTitle(): String = resources.getString(R.string.app_title)

    override fun getAppDescription(): String =
        resources.getString(R.string.app_description)
}