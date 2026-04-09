package com.rve.systemmonitor.utils

import android.content.Context
import android.util.Log
import android.view.WindowManager
import kotlin.math.pow
import kotlin.math.sqrt

object DisplayUtils {
    private const val TAG = "DisplayUtils"

    fun getResolution(context: Context): String = runCatching {
        val metrics = context.resources.displayMetrics
        "${metrics.widthPixels}x${metrics.heightPixels}"
    }.getOrElse {
        Log.e(TAG, "getResolution: ${it.message}", it)
        "unknown"
    }

    fun getRefreshRate(context: Context): Int = runCatching {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.refreshRate.toInt()
    }.getOrElse {
        Log.e(TAG, "getRefreshRate: ${it.message}", it)
        0
    }

    fun getDensityDpi(context: Context): Int = runCatching {
        context.resources.displayMetrics.densityDpi
    }.getOrElse {
        Log.e(TAG, "getDensityDpi: ${it.message}", it)
        0
    }

    fun getScreenSizeInches(context: Context): Double = runCatching {
        val metrics = context.resources.displayMetrics
        val widthInches = metrics.widthPixels.toDouble() / metrics.xdpi
        val heightInches = metrics.heightPixels.toDouble() / metrics.ydpi

        val diagonal = sqrt(widthInches.pow(2.0) + heightInches.pow(2.0))

        "%.2f".format(java.util.Locale.US, diagonal).toDouble()
    }.getOrElse {
        Log.e(TAG, "getScreenSizeInches: ${it.message}", it)
        0.0
    }
}
