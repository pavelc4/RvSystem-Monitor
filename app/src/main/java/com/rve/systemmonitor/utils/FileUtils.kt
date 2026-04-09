package com.rve.systemmonitor.utils

import java.io.File

object FileUtils {
    fun readFileString(filePath: String): String? {
        return try {
            val file = File(filePath)
            if (file.exists() && file.canRead()) {
                file.readText().trim()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun readFileInt(filePath: String): Int? {
        return try {
            val file = File(filePath)
            if (file.exists() && file.canRead()) {
                file.readText().trim().toInt()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun readFileLong(filePath: String): Long? {
        return try {
            val file = File(filePath)
            if (file.exists() && file.canRead()) {
                file.readText().trim().toLong()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun readFileFloat(filePath: String): Float? {
        return try {
            val file = File(filePath)
            if (file.exists() && file.canRead()) {
                file.readText().trim().toFloat()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
