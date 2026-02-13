package com.anbu.deviceinfo.ui.theme.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.offline.Download
import com.anbu.deviceinfo.data.DeviceRepository
import com.anbu.deviceinfo.ui.theme.components.SpecCard
import com.anbu.deviceinfo.ui.theme.components.SmallInfoCard
import com.anbu.deviceinfo.ui.theme.components.LargeInfoCard
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun DashboardScreen() {

    val context = LocalContext.current

    val displaySize = DeviceRepository.getDisplaySize(context)
    val inchesFormatted = String.format("%.2f", displaySize.first)
    val cmFormatted = String.format("%.2f", displaySize.second)

    val provider = DeviceRepository.getNetworkProvider(context)

    val totalRam = DeviceRepository.getTotalRam(context) / (1024 * 1024)

    var usedRam by remember { mutableStateOf(0L) }
    var usedPercent by remember { mutableStateOf(0) }

    var downloadSpeed by remember { mutableStateOf("0 KB/s") }
    var uploadSpeed by remember { mutableStateOf("0 KB/s") }

    var lastRx by remember { mutableStateOf(DeviceRepository.getRxBytes()) }
    var lastTx by remember { mutableStateOf(DeviceRepository.getTxBytes()) }

    val totalRamBytes = DeviceRepository.getTotalRam(context)

    LaunchedEffect(Unit) {
        while (true) {

            // RAM
            val usedBytes = DeviceRepository.getUsedRam(context)
            usedRam = usedBytes / (1024 * 1024)
            usedPercent = ((usedBytes.toDouble() / totalRamBytes) * 100).toInt()

            // Internet
            val newRx = DeviceRepository.getRxBytes()
            val newTx = DeviceRepository.getTxBytes()

            val rxSpeed = (newRx - lastRx) / 1024
            val txSpeed = (newTx - lastTx) / 1024

            downloadSpeed = "$rxSpeed KB/s"
            uploadSpeed = "$txSpeed KB/s"

            lastRx = newRx
            lastTx = newTx

            delay(1000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // 🔹 Row 1 - Internet Speeds
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            SmallInfoCard(
                modifier = Modifier.weight(1f),
                title = "Download",
                value = downloadSpeed,
                icon = Icons.Default.Download
            )

            SmallInfoCard(
                modifier = Modifier.weight(1f),
                title = "Upload",
                value = uploadSpeed,
                icon = Icons.Default.Upload
            )
        }

        Spacer(Modifier.height(16.dp))

        // 🔹 Processor (Full width)
        LargeInfoCard(
            title = "Processor",
            value = DeviceRepository.getProcessorFromDatabase(context),
            icon = Icons.Default.Memory
        )

        Spacer(Modifier.height(16.dp))

        // 🔹 Display & Android
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            SmallInfoCard(
                modifier = Modifier.weight(1f),
                title = "Display",
                value = "$inchesFormatted in ($cmFormatted cm)",
                icon = Icons.Default.PhoneAndroid
            )

            ElevatedCard(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        Icons.Default.Android,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        DeviceRepository.getUiName(),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        DeviceRepository.getAndroidVersion(),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

        }

        Spacer(Modifier.height(16.dp))

        // 🔹 RAM
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            SmallInfoCard(
                modifier = Modifier.weight(1f),
                title = "RAM",
                value = "$totalRam MB",
                icon = Icons.Default.Storage
            )

            SmallInfoCard(
                modifier = Modifier.weight(1f),
                title = "RAM Used",
                value = "$usedRam MB ($usedPercent%)",
                icon = Icons.Default.Speed
            )
        }
    }
}

