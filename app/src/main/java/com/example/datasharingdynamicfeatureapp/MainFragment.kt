package com.example.datasharingdynamicfeatureapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.commit
import com.example.datasharingdynamicfeatureapp.ui.theme.DataSharingDynamicFeatureAppTheme
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            DataSharingDynamicFeatureAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainFragmentContent()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainFragmentContent(modifier: Modifier = Modifier) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column {
            Text(
                text = "Dynamic Feature Data Sharing",
                style = MaterialTheme.typography.headlineMedium
            )
            StandardSpacer()
            Text(
                "This app demonstrates data sharing between an activity that lives in the base app " +
                        "and a fragment that lives in a dynamic feature module using dependency injection " +
                        "via Dagger 2 and the Android architecture ViewModel. \n\nNote that according to " +
                        "the official documentation: \"In feature modules, the way that modules usually " +
                        "depend on each other is inverted. Therefore, Hilt cannot process annotations in " +
                        "feature modules. You must use Dagger to perform dependency injection in your " +
                        "feature modules.\""
            )
            StandardSpacer()

            val activity = LocalContext.current as? FragmentActivity ?: return@Column
            Button(
                modifier = modifier,
                onClick = {
                    installDynamicFeature(
                        activity.applicationContext,
                        {
                            launchDynamicFeature1(activity)
                        },
                        {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Successfully installed :dynamicFeature1 module")
                            }
                            launchDynamicFeature1(activity)
                        }) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Failed to install :dynamicFeature1 module due to ${it.message}")
                        }
                    }
                }
            ) {
                Text("Launch dynamic feature 1")
            }
        }

    }
}

private fun installDynamicFeature(
    appContext: Context,
    onAlreadyInstalled: () -> Unit,
    onInstallationSuccess: (Int) -> Unit,
    onInstallationFailure: (Exception) -> Unit
) {
    // TODO Inject SplitInstallManager
    val splitInstallManager = SplitInstallManagerFactory.create(appContext)
    if (splitInstallManager.installedModules.contains("dynamicFeature1")) {
        onAlreadyInstalled()
        return
    }

    val request = SplitInstallRequest.newBuilder()
        .addModule("dynamicFeature1")
        .build()
    SplitInstallManagerFactory.create(appContext)
        .startInstall(request)
        .addOnSuccessListener {
            //TODO Inject SnackbarBuilder
            onInstallationSuccess(it)
        }
        .addOnFailureListener {
            onInstallationFailure(it)
        }
}

private fun launchDynamicFeature1(activity: FragmentActivity) {
    val fragment = FragmentFactory().instantiate(
        activity.classLoader, "com.example.dynamicfeature1.DynamicFeature1Fragment"
    )
    activity.supportFragmentManager.commit {
        replace(R.id.fragmentContainer, fragment)
    }
}

@Composable
private fun StandardSpacer() {
    Spacer(Modifier.height(16.dp))
}