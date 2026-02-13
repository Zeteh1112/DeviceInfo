package com.anbu.deviceinfo.ui.theme.screens

import androidx.compose.runtime.Composable
import com.anbu.deviceinfo.data.DeviceRepository
import com.anbu.deviceinfo.ui.theme.components.SpecCard

@Composable
fun DeviceScreen() {

    SpecCard("Manufacturer", DeviceRepository.manufacturer())
    SpecCard("Model", DeviceRepository.model())
    SpecCard("Board", DeviceRepository.board())
    SpecCard("Hardware", DeviceRepository.hardware())
}
