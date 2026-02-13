package com.anbu.deviceinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.anbu.deviceinfo.ui.theme.screens.MainScreen
import com.anbu.deviceinfo.ui.theme.DeviceInfoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DeviceInfoTheme {
                MainScreen()
            }
        }
    }
}