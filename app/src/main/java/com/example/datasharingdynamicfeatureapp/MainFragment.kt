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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.datasharingdynamicfeatureapp.ui.theme.DataSharingDynamicFeatureAppTheme
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val mainViewModel: MainViewModel by activityViewModels {
        viewModelProviderFactory
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

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
                    MainFragmentContent(viewModel = mainViewModel)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainFragmentContent(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column {
            Text(
                text = viewModel.getAppTitle(),
                style = MaterialTheme.typography.headlineMedium
            )
            StandardSpacer()
            Text(viewModel.getAppDescription())
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