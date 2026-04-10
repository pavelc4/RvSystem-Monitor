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
}
