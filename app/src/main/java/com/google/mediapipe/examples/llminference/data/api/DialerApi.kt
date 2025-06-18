package com.google.mediapipe.examples.llminference.data.api

import com.google.mediapipe.examples.llminference.data.model.DeviceSpecsRequest
import com.google.mediapipe.examples.llminference.data.model.RecommendedModelsResponse
import retrofit2.http.Body
import retrofit2.http.POST

const val BASE_URL = "http://3.93.168.96:8010/"
const val RECOMMENDED_MODEL_URL = "recommend-models"
interface DialerApi {

    @POST(RECOMMENDED_MODEL_URL)
    suspend fun getRecommendedModels(@Body request: DeviceSpecsRequest): RecommendedModelsResponse
}