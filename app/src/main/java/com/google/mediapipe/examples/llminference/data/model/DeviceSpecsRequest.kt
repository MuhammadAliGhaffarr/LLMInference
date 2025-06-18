package com.google.mediapipe.examples.llminference.data.model

data class DeviceSpecsRequest(
    val ram_gb: Float,
    val has_npu: Boolean,
    val gpu_compute_units: Int,
    val cpu_cores: Int,
    val cpu_clock_ghz: Float
)
