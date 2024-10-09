package com.vl.victorlekweuwamoody

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.vl.victorlekweuwamoody.features.home.HomeScreen
import com.vl.victorlekweuwamoody.features.home.HomeViewModel
import com.vl.victorlekweuwamoody.ui.theme.VictorLekweuwaMoodyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val homeViewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Showing the splash screen for 1 second before showing this activity.

        installSplashScreen().apply {
            setKeepOnScreenCondition() {
                homeViewModel.isLoading.value
            }
        }

        enableEdgeToEdge()

        setContent {
            VictorLekweuwaMoodyTheme {
                HomeScreen(homeViewModel)
            }
        }

    }

}

