package com.anbu.deviceinfo.ui.theme.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.anbu.deviceinfo.data.DeviceRepository
import com.anbu.deviceinfo.ui.theme.components.SpecCard


@Composable
fun BatteryScreen() {

    val context = LocalContext.current

    SpecCard("Battery Level", "${DeviceRepository.getBatteryLevel(context)} %")
}
