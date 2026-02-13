package com.anbu.deviceinfo.ui.theme.screens

import androidx.compose.runtime.Composable
import com.anbu.deviceinfo.data.DeviceRepository
import com.anbu.deviceinfo.ui.theme.components.SpecCard

@Composable
fun CpuScreen() {

    SpecCard("CPU Cores", DeviceRepository.cpuCores().toString())
    SpecCard("Hardware", DeviceRepository.hardware())
}
