package com.anbu.deviceinfo.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SmallInfoCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                title,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(Modifier.height(4.dp))

            Text(
                value,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun LargeInfoCard(
    title: String,
    value: String,
    icon: ImageVector
) {
    ElevatedCard(
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text(title, style = MaterialTheme.typography.labelMedium)

            Spacer(Modifier.height(6.dp))

            Text(
                value,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

