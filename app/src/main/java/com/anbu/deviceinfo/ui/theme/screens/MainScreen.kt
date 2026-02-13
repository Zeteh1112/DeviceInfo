package com.anbu.deviceinfo.ui.theme.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.anbu.deviceinfo.ui.theme.components.FloatingIconBottomBar


data class NavItem(
    val title: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navItems = listOf(
        NavItem("Home", Icons.Default.Dashboard),
        NavItem("Device", Icons.Default.PhoneAndroid),
        NavItem("System", Icons.Default.Android),
        NavItem("CPU", Icons.Default.Memory),
        NavItem("Battery", Icons.Default.BatteryFull),
        NavItem("Display", Icons.Default.DisplaySettings)
    )

    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Main Content
            when (selectedIndex) {
                0 -> DashboardScreen()
                1 -> DeviceScreen()
                2 -> SystemScreen()
                3 -> CpuScreen()
                4 -> BatteryScreen()
                5 -> DisplayScreen()
            }

            // Floating Bottom Bar (FORCE bottom)
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                FloatingIconBottomBar(
                    selectedIndex = selectedIndex,
                    onItemSelected = { selectedIndex = it }
                )
            }
        }
    }


}