package com.google.mediapipe.examples.llminference

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mediapipe.examples.llminference.data.model.RecommendedModelsResponse
import com.google.mediapipe.examples.llminference.data.repository.Repository
import com.google.mediapipe.examples.llminference.data.utilities.DeviceInfoUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModelRecommendationViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun getRecommendedModels(
        context: Context,
        onSuccess: (RecommendedModelsResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.getRecommendedModels(DeviceInfoUtils.getDeviceInfo(context))
                onSuccess(response)
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }
}