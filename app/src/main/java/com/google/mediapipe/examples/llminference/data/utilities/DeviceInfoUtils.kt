package com.google.mediapipe.examples.llminference.data.utilities
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import com.google.mediapipe.examples.llminference.data.model.DeviceSpecsRequest

object DeviceInfoUtils {

        fun getDeviceInfo(context: Context): DeviceSpecsRequest {
            val ramGB = getTotalRAM(context)
            val hasNPU = checkNPU()
            val gpuComputeUnits = getGPUComputeUnits()
            val cpuCores = Runtime.getRuntime().availableProcessors()
            val cpuClockGHz = getMaxCpuClockSpeedGHz()

            return DeviceSpecsRequest(
                ram_gb = String.format("%.1f", ramGB).toFloat(),
                has_npu = hasNPU,
                gpu_compute_units = gpuComputeUnits,
                cpu_cores = cpuCores,
                cpu_clock_ghz = String.format("%.1f", cpuClockGHz).toFloat()
            )
        }

        private fun getTotalRAM(context: Context): Double {
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val memInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memInfo)
            return memInfo.totalMem / (1024.0 * 1024.0 * 1024.0)
        }

        private fun checkNPU(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P // Example heuristic
        }

        private fun getGPUComputeUnits(): Int {
            // Hard to get directly in Android; could be estimated based on GPU model if known
            return 12 // Placeholder value, needs vendor-specific implementation
        }

        private fun getMaxCpuClockSpeedGHz(): Double {
            return try {
                val cpuInfo = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq".toFile().readText().trim()
                cpuInfo.toLong() / 1_000_000.0 // Convert kHz to GHz
            } catch (e: Exception) {
                2.5 // Default fallback value
            }
        }

    private fun String.toFile() = java.io.File(this)

}