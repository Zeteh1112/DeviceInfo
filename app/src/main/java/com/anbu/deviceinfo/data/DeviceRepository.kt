package com.anbu.deviceinfo.data

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import kotlin.math.sqrt
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.TrafficStats
import android.telephony.TelephonyManager
import com.anbu.deviceinfo.data.model.SocDatabase
import java.io.File

object DeviceRepository {

    // -------- RAM --------

    fun getTotalRam(context: Context): Long {
        val actManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        return memInfo.totalMem
    }

    fun getUsedRam(context: Context): Long {
        val actManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        return memInfo.totalMem - memInfo.availMem
    }

    // -------- CPU --------

    @RequiresApi(Build.VERSION_CODES.S)
    fun getProcessor(): String {
        return Build.SOC_MODEL ?: Build.HARDWARE
    }

    fun getManufacturer(): String {
        return Build.MANUFACTURER
    }

    // -------- DISPLAY --------

    fun getDisplaySize(context: Context): Pair<Double, Double> {

        val metrics = context.resources.displayMetrics

        val widthInches = metrics.widthPixels.toDouble() / metrics.xdpi
        val heightInches = metrics.heightPixels.toDouble() / metrics.ydpi

        val diagonalInches = kotlin.math.sqrt(
            widthInches * widthInches +
                    heightInches * heightInches
        )

        val diagonalCm = diagonalInches * 2.54

        return Pair(diagonalInches, diagonalCm)
    }


    // -------- ANDROID --------

    fun getAndroidVersion(): String {
        return "Android ${Build.VERSION.RELEASE}"
    }

    fun getBatteryLevel(context: Context): Int {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as android.os.BatteryManager
        return bm.getIntProperty(android.os.BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

    // -------- INTERNET PROVIDER --------

    fun getNetworkProvider(context: Context): String {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return "No Connection"
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return "No Connection"

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                telephonyManager.networkOperatorName ?: "Mobile Data"
            }

            else -> "Unknown"
        }
    }

// -------- INTERNET SPEED --------

    fun getRxBytes(): Long = TrafficStats.getTotalRxBytes()
    fun getTxBytes(): Long = TrafficStats.getTotalTxBytes()


    // -------- PROCESSOR FULL INFO --------
    fun getProcessorFromDatabase(context: Context): String {

        val hardware = Build.HARDWARE?.lowercase() ?: return "Unknown"
        val hardwareUpper = hardware.uppercase()

        SocDatabase.loadDatabase(context)

        val socData = SocDatabase.getSoc(hardware)

        return if (socData != null) {
            "${socData.name} ($hardwareUpper)"
        } else {
            hardwareUpper
        }
}


    // -------- UI NAME DETECTION --------

    fun getUiName(): String {

        val manufacturer = Build.MANUFACTURER.lowercase()
        val display = Build.DISPLAY.lowercase()

        return when {

            manufacturer.contains("samsung") -> "One UI"

            manufacturer.contains("xiaomi") ||
                    manufacturer.contains("redmi") ||
                    manufacturer.contains("poco") -> "MIUI / HyperOS"

            manufacturer.contains("oppo") -> "ColorOS"

            manufacturer.contains("vivo") -> {
                if (display.contains("origin"))
                    "OriginOS"
                else
                    "Funtouch OS"
            }

            manufacturer.contains("realme") -> "Realme UI"

            manufacturer.contains("motorola") -> "Hello UI"

            manufacturer.contains("oneplus") -> "OxygenOS"

            manufacturer.contains("huawei") -> "EMUI"

            manufacturer.contains("honor") -> "Magic UI"

            else -> "Android"
        }
    }


    fun getCpuCores(): Int {
        return Runtime.getRuntime().availableProcessors()
    }

    // Read CPU frequencies from system files
    fun getCpuFrequencies(): List<String> {

        val result = mutableListOf<String>()

        try {
            val cores = getCpuCores()

            for (i in 0 until cores) {
                val file = File("/sys/devices/system/cpu/cpu$i/cpufreq/cpuinfo_max_freq")
                if (file.exists()) {
                    val freq = file.readText().trim().toLong() / 1000 // kHz → MHz
                    result.add("Core $i : ${freq} MHz")
                }
            }
        } catch (e: Exception) {
            result.add("Frequency unavailable")
        }

        return result
    }

    // Basic CPU architecture guess
    fun getCpuArchitecture(): String {
        return Build.SUPPORTED_ABIS.joinToString()
    }


    fun model() = Build.MODEL
    fun manufacturer() = Build.MANUFACTURER
    fun androidVersion() = Build.VERSION.RELEASE
    fun apiLevel() = Build.VERSION.SDK_INT
    fun board() = Build.BOARD
    fun hardware() = Build.HARDWARE
    fun fingerprint() = Build.FINGERPRINT

    fun cpuCores(): Int = Runtime.getRuntime().availableProcessors()

    fun displayInfo(context: Context): DisplayMetrics {
        return context.resources.displayMetrics
    }
    @RequiresApi(Build.VERSION_CODES.R)
    fun refreshRate(context: Context): Float {
        return context.display?.refreshRate ?: 0f
    }
}
