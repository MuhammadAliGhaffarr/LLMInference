package com.google.mediapipe.examples.llminference

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.mediapipe.examples.llminference.data.model.ModelRecommendation
import com.google.mediapipe.examples.llminference.data.utilities.START_SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RecommendedModelsScreen(
    navController: NavController,
    viewModel: ModelRecommendationViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var selectedItem by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val recommendedModels by remember { mutableStateOf(mutableListOf<ModelRecommendation>()) }

    LaunchedEffect(Unit) {
        viewModel.getRecommendedModels(
            context = context,
            onSuccess = { response ->
                recommendedModels.clear()
                recommendedModels.addAll(response.recommended_models)
            },
            onError = { error ->
                Log.d("AliTag", "Error: $error")
            }
        )
    }

    recommendedModels.addAll(arrayOf(ModelRecommendation("openai-community/gpt2",1000000000.0,1.93,2.9,true,true,true,"https://d1apbb4wrgy3cq.cloudfront.net/gemma-2b-it-cpu-int4.bin"),ModelRecommendation("openai-community/gpt2",1000000000.0,1.93,2.9,true,true,true,"https://d1apbb4wrgy3cq.cloudfront.net/gemma-2b-it-cpu-int4.bin"),ModelRecommendation("openai-community/gpt2",1000000000.0,1.93,2.9,true,true,true,"https://d1apbb4wrgy3cq.cloudfront.net/gemma-2b-it-cpu-int4.bin"),ModelRecommendation("openai-community/gpt2",1000000000.0,1.93,2.9,true,true,true,"https://d1apbb4wrgy3cq.cloudfront.net/gemma-2b-it-cpu-int4.bin"),ModelRecommendation("openai-community/gpt2",1000000000.0,1.93,2.9,true,true,true,"https://d1apbb4wrgy3cq.cloudfront.net/gemma-2b-it-cpu-int4.bin"),ModelRecommendation("openai-community/gpt2",1000000000.0,1.93,2.9,true,true,true,"https://d1apbb4wrgy3cq.cloudfront.net/gemma-2b-it-cpu-int4.bin"),ModelRecommendation("openai-community/gpt2",1000000000.0,1.93,2.9,true,true,true,"https://d1apbb4wrgy3cq.cloudfront.net/gemma-2b-it-cpu-int4.bin"),ModelRecommendation("openai-community/gpt2",1000000000.0,1.93,2.9,true,true,true,"https://d1apbb4wrgy3cq.cloudfront.net/gemma-2b-it-cpu-int4.bin"),ModelRecommendation("openai-community/gpt2",1000000000.0,1.93,2.9,true,true,true,"https://d1apbb4wrgy3cq.cloudfront.net/gemma-2b-it-cpu-int4.bin")))

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedItem ?: "Please select a model",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                singleLine = true
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                recommendedModels.forEach { model ->
                    DropdownMenuItem(
                        text = { Text(model.model_id) },
                        onClick = {
                            selectedItem = model.model_id
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val selectedModel = recommendedModels.find { it.model_id == selectedItem }
                selectedModel?.let {
                    val serializationModel = Gson().toJson(it)
                    navController.navigate("$START_SCREEN/${serializationModel}")
                }
            },
            enabled = selectedItem != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}
