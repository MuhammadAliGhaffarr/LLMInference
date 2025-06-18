package com.google.mediapipe.examples.llminference.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.mediapipe.examples.llminference.RecommendedModelsScreen
import com.google.mediapipe.examples.llminference.SelectionRoute
import com.google.mediapipe.examples.llminference.data.utilities.RECOMMEDED_MODELS_SCREEN
import com.google.mediapipe.examples.llminference.data.utilities.START_SCREEN

@Composable
fun NavigationHost(intent:Intent) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = RECOMMEDED_MODELS_SCREEN) {
        composable(route = RECOMMEDED_MODELS_SCREEN) {
            RecommendedModelsScreen(navController = navController)
        }
        composable(
            route = "$START_SCREEN/{model}",
            arguments = listOf(navArgument("model") { type = NavType.StringType })
        ) {
            val serializationModel = it.arguments?.getString("model")
            val showPopup = intent.getBooleanExtra("showPopup", false)

            SelectionRoute(navController = navController, serializationModel = serializationModel,showPopup = showPopup)
        }
    }
}