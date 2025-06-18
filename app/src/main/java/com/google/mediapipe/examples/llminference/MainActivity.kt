package com.google.mediapipe.examples.llminference

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.mediapipe.examples.llminference.data.utilities.CHAT_SCREEN
import com.google.mediapipe.examples.llminference.data.utilities.DeviceInfoUtils
import com.google.mediapipe.examples.llminference.data.utilities.LOAD_SCREEN
import com.google.mediapipe.examples.llminference.data.utilities.RECOMMEDED_MODELS_SCREEN
import com.google.mediapipe.examples.llminference.data.utilities.START_SCREEN
import com.google.mediapipe.examples.llminference.navigation.NavigationHost
import com.google.mediapipe.examples.llminference.services.ModelDownloadService
import com.google.mediapipe.examples.llminference.services.NotificationService
import com.google.mediapipe.examples.llminference.ui.theme.LLMInferenceTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var notificationService: NotificationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationService = NotificationService()

        Log.d("AliTag", DeviceInfoUtils.getDeviceInfo(this).toString())
        val showPopup = intent?.getBooleanExtra("showPopup", false) ?: false

        setContent {
            notificationService.RequestNotificationPermission()
            LLMInferenceTheme {
                Scaffold(
                    topBar = { AppBar() }
                ) { innerPadding ->
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background,
                    ) {

                        NavigationHost(intent)

//                        val navController = rememberNavController()
//
//                        NavHost(
//                            navController = navController,
//                            startDestination = RECOMMEDED_MODELS_SCREEN
//                        ) {
//                            composable(RECOMMEDED_MODELS_SCREEN) {
//                                RecommendedModelsScreen(
//                                    onModelSelected = { it ->
//                                        navController.navigate(START_SCREEN) {
//                                            popUpTo(RECOMMEDED_MODELS_SCREEN) { inclusive = true }
//                                            launchSingleTop = true
//                                        }
//                                    }
//                                )
//                            }
//                            composable(START_SCREEN) {
//                                SelectionRoute(
//                                    onModelSelected = {
//                                        navController.navigate(LOAD_SCREEN) {
//                                            popUpTo(START_SCREEN) { inclusive = true }
//                                            launchSingleTop = true
//                                        }
//                                    },
//                                    showPopup = showPopup
//                                )
//                            }
//
//                            composable(LOAD_SCREEN) {
//                                LoadingRoute(
//                                    onModelLoaded = {
//                                        navController.navigate(CHAT_SCREEN) {
//                                            popUpTo(LOAD_SCREEN) { inclusive = true }
//                                            launchSingleTop = true
//                                        }
//                                    },
//                                    onGoBack = {
//                                        navController.navigate(START_SCREEN) {
//                                            popUpTo(LOAD_SCREEN) { inclusive = true }
//                                            launchSingleTop = true
//                                        }
//                                    },
//                                    modelDownloadService = ModelDownloadService(),
//                                    notificationService = notificationService
//                                )
//                            }
//
//                            composable(CHAT_SCREEN) {
//                                ChatRoute(
//                                    onClose = {
//                                        navController.navigate(START_SCREEN) {
//                                            popUpTo(LOAD_SCREEN) { inclusive = true }
//                                            launchSingleTop = true
//                                        }
//                                    })
//                            }
//                        }
                    }
                }
            }
        }
    }

    // TopAppBar is marked as experimental in Material 3
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppBar() {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
            Box(
                modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Text(
                    text = stringResource(R.string.disclaimer),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}
