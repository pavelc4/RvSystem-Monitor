package com.rve.systemmonitor.utils

import android.os.Build
import android.util.Log

object CpuUtils {
    private const val TAG = "CpuUtils"

    /**
     * Mengambil nama pembuat Chipset (misal: Qualcomm, MediaTek, Samsung)
     */
    fun getSocManufacturer(): String = runCatching {
        // Build.SOC_MANUFACTURER tersedia mulai API 31
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

    /**
     * Mengambil model Chipset (misal: SM8550, MT6893, Exynos 2200)
     */
    fun getSocModel(): String = runCatching {
        // Build.SOC_MODEL tersedia mulai API 31
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

    /**
     * Mengambil jumlah inti (Cores) prosesor yang tersedia
     */
    fun getCoreCount(): Int = runCatching {
        Runtime.getRuntime().availableProcessors()
    }.getOrElse {
        Log.e(TAG, "getCoreCount: ${it.message}", it)
        0
    }
}
