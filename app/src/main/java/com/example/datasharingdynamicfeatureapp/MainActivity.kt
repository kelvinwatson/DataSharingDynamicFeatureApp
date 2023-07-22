package com.example.datasharingdynamicfeatureapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.google.android.play.core.splitcompat.SplitCompat
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : FragmentActivity(), HasAndroidInjector {

    @Inject
    internal lateinit var fragmentInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): DispatchingAndroidInjector<Any> = fragmentInjector
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.installActivity(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.commit {
            add(R.id.fragmentContainer, MainFragment())
        }
    }
}