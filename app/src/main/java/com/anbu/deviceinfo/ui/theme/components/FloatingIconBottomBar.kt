package com.anbu.deviceinfo.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class IconNavItem(
    val icon: ImageVector
)

@Composable
fun FloatingIconBottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {

    val items = listOf(
        IconNavItem(Icons.Default.Dashboard),
        IconNavItem(Icons.Default.PhoneAndroid),
        IconNavItem(Icons.Default.Android),
        IconNavItem(Icons.Default.Memory),
        IconNavItem(Icons.Default.BatteryFull),
        IconNavItem(Icons.Default.DisplaySettings)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(40.dp)
                )
                .padding(horizontal = 24.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            items.forEachIndexed { index, item ->

                val isSelected = selectedIndex == index

                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        Color.Gray,
                    modifier = Modifier
                        .size(26.dp)
                        .clickable {
                            onItemSelected(index)
                        }
                )
            }
        }
    }
}
