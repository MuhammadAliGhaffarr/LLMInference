package com.google.mediapipe.examples.llminference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.mediapipe.examples.llminference.data.utilities.LOAD_SCREEN

@Composable
internal fun SelectionRoute(
    navController: NavController,
    serializationModel: String?,
    showPopup:Boolean,
) {
    ShowPopup(showPopup)
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(Model.entries) { model ->
            Button(
                onClick = {
                    InferenceModel.model = model
                    navController.navigate("$LOAD_SCREEN/${serializationModel}")
                },
            ) {
                Text(model.toString())
            }
        }
    }
}

@Composable
fun ShowPopup(showPopup: Boolean) {
    var showDialog by remember { mutableStateOf(showPopup) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Welcome Back!") },
                text = { Text("Your Downloaded model has been completed.") },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
