package com.anbu.deviceinfo.ui.theme.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.anbu.deviceinfo.data.DeviceRepository
import com.anbu.deviceinfo.ui.theme.components.SpecCard

@Composable
fun DisplayScreen() {

    val context = LocalContext.current
    val metrics = DeviceRepository.displayInfo(context)

    SpecCard("Resolution", "${metrics.widthPixels} x ${metrics.heightPixels}")
    SpecCard("Density", "${metrics.densityDpi} dpi")
    SpecCard("Refresh Rate", "${DeviceRepository.refreshRate(context)} Hz")
}
