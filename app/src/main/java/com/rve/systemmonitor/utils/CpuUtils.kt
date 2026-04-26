package com.rve.systemmonitor.utils

import android.os.Build
import android.util.Log

object CpuUtils {
    private const val TAG = "CpuUtils"

    fun getSocManufacturer(): String = runCatching {
        val manufacturer = Build.SOC_MANUFACTURER
        if (manufacturer != Build.UNKNOWN) {
            manufacturer.replaceFirstChar { it.uppercase() }
        } else {
            "Unknown"
        }
    }.getOrElse {
        Log.e(TAG, "getSocManufacturer: ${it.message}", it)
        "Unknown"
    }

    fun getSocModel(): String = runCatching {
        val model = Build.SOC_MODEL
        if (model != Build.UNKNOWN) {
            model.uppercase()
        } else {
            "Unknown"
        }
    }.getOrElse {
        Log.e(TAG, "getSocModel: ${it.message}", it)
        "Unknown"
    }

    fun getCoreCount(): Int = runCatching {
        Runtime.getRuntime().availableProcessors()
    }.getOrElse {
        Log.e(TAG, "getCoreCount: ${it.message}", it)
        0
    }

    fun getCoreFrequency(coreId: Int, type: String): String {
        val fileName = when (type) {
            "max_info" -> "cpuinfo_max_freq"
            "min_info" -> "cpuinfo_min_freq"
            else -> "scaling_${type}_freq"
        }
        val path = "/sys/devices/system/cpu/cpu$coreId/cpufreq/$fileName"
        return readSystemFile(path)?.let {
            val freqKhz = it.trim().toLongOrNull() ?: 0L
            formatFrequency(freqKhz)
        } ?: "N/A"
    }

    fun getCoreGovernor(coreId: Int): String {
        val path = "/sys/devices/system/cpu/cpu$coreId/cpufreq/scaling_governor"
        return readSystemFile(path)?.trim() ?: "N/A"
    }

    private fun readSystemFile(path: String): String? = runCatching {
        java.io.File(path).readText()
    }.getOrNull()

    private fun formatFrequency(freqKhz: Long): String {
        return if (freqKhz >= 1000000) {
            String.format(java.util.Locale.US, "%.2f GHz", freqKhz / 1000000.0)
        } else {
            "${freqKhz / 1000} MHz"
        }
    }
}
