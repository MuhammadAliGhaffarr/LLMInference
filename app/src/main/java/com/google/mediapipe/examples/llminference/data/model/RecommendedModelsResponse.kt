package com.google.mediapipe.examples.llminference.data.model

data class ModelRecommendation(
    val model_id: String = "1",
    val params: Double = 0.0,
    val estimated_ram_gb: Double = 0.0,
    val min_ram_gb: Double = 0.0,
    val npu_optimized: Boolean = false,
    val gpu_suitable: Boolean = false,
    val cpu_suitable: Boolean = false,
    val link: String = ""
)

data class RecommendedModelsResponse(
    val recommended_models: List<ModelRecommendation>
)