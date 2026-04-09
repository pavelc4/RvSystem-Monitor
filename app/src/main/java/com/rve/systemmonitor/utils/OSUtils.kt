package com.rve.systemmonitor.utils

import android.os.Build
import android.util.Log

object OSUtils {
    private const val TAG = "OSUtils"

    fun getAndroidVersion(): String = runCatching {
        Build.VERSION.RELEASE
    }.getOrElse {
        Log.e(TAG, "getAndroidVersion: ${it.message}", it)
        "unknown"
    }

    fun getSdkInt(): Int = runCatching {
        Build.VERSION.SDK_INT
    }.getOrElse {
        Log.e(TAG, "getSdkInt: ${it.message}", it)
        0
    }

    fun getDessertName(sdkInt: Int): String {
        return when (sdkInt) {
            36 -> "Baklava"
            35 -> "Vanilla Ice Cream"
            34 -> "Upside Down Cake"
            else -> "unknown"
        }
    }

    fun getSecurityPatch(): String = runCatching {
        Build.VERSION.SECURITY_PATCH
    }.getOrElse {
        Log.e(TAG, "getSecurityPatch: ${it.message}", it)
        "unknown"
    }
}
