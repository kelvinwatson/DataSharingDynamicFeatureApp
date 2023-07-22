package com.example.datasharingdynamicfeatureapp

import androidx.lifecycle.ViewModel
import javax.inject.Inject
class MainViewModel @Inject constructor(
    private val mainFragmentStringProvider: MainFragmentStringProvider
) : ViewModel() {

    fun getAppTitle() = mainFragmentStringProvider.getAppTitle()

    fun getAppDescription() = mainFragmentStringProvider.getAppDescription()
}