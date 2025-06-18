package com.google.mediapipe.examples.llminference.data.repository

import com.google.mediapipe.examples.llminference.data.model.DeviceSpecsRequest
import com.google.mediapipe.examples.llminference.data.model.RecommendedModelsResponse
import com.google.mediapipe.examples.llminference.data.network.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(private val remoteDataSource: RemoteDataSource) {
    suspend fun getRecommendedModels(request: DeviceSpecsRequest): RecommendedModelsResponse {
        return remoteDataSource.invoke(request)
    }
}