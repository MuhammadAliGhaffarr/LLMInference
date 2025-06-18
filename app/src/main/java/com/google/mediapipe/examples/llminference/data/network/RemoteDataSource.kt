package com.google.mediapipe.examples.llminference.data.network

import com.google.mediapipe.examples.llminference.data.api.DialerApi
import com.google.mediapipe.examples.llminference.data.model.DeviceSpecsRequest
import com.google.mediapipe.examples.llminference.data.model.RecommendedModelsResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val dialerApi: DialerApi
) {
    suspend fun invoke(request: DeviceSpecsRequest): RecommendedModelsResponse {
        return dialerApi.getRecommendedModels(request)
    }
}