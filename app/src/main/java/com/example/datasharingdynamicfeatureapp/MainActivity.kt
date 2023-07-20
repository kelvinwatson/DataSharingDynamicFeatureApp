package com.example.datasharingdynamicfeatureapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.datasharingdynamicfeatureapp.ui.theme.DataSharingDynamicFeatureAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataSharingDynamicFeatureAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content(modifier: Modifier = Modifier) {
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
        Button(
            modifier = modifier,
            onClick = {}
        ) {
            Text("Launch dynamic feature")
        }
    }
}

@Composable
private fun StandardSpacer() {
    Spacer(Modifier.height(16.dp))
}