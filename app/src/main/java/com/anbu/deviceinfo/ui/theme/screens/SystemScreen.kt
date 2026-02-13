package com.anbu.deviceinfo.ui.theme.screens

import androidx.compose.runtime.Composable
import com.anbu.deviceinfo.data.DeviceRepository
import com.anbu.deviceinfo.ui.theme.components.SpecCard

@Composable
fun SystemScreen() {

    SpecCard("Android Version", DeviceRepository.androidVersion())
    SpecCard("API Level", DeviceRepository.apiLevel().toString())
    SpecCard("Fingerprint", DeviceRepository.fingerprint())
}
