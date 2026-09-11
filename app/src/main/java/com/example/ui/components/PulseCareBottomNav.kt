package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NavTab
import com.example.ui.theme.Primary
import com.example.ui.theme.Secondary

@Composable
fun PulseCareBottomNav(
    currentTab: NavTab,
    onTabSelected: (NavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
        shadowElevation = 8.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            NavTab.entries.forEach { tab ->
                val isSelected = currentTab == tab
                val icon = when (tab) {
                    NavTab.HOME -> if (isSelected) Icons.Filled.CalendarToday else Icons.Outlined.CalendarToday
                    NavTab.BOOK -> if (isSelected) Icons.Filled.MedicalServices else Icons.Outlined.MedicalServices
                    NavTab.TRACKER -> if (isSelected) Icons.Filled.MonitorHeart else Icons.Outlined.MonitorHeart
                    NavTab.RECORDS -> if (isSelected) Icons.Filled.Assignment else Icons.Outlined.Assignment
                    NavTab.PROFILE -> if (isSelected) Icons.Filled.Person else Icons.Outlined.Person
                }

                PulseCareNavItem(
                    tab = tab,
                    isSelected = isSelected,
                    icon = icon,
                    onClick = { onTabSelected(tab) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun PulseCareNavItem(
    tab: NavTab,
    isSelected: Boolean,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeColor = Primary
    val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
    val color = if (isSelected) activeColor else inactiveColor

    val interactionSource = remember { MutableInteractionSource() }

    // Live ping for Tracker tab
    val infiniteTransition = rememberInfiniteTransition(label = "ping")
    val pingScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pingScale"
    )
    val pingAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pingAlpha"
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 6.dp)
            .testTag("nav_tab_${tab.name.lowercase()}"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = tab.label,
                tint = color,
                modifier = Modifier.size(24.dp)
            )

            if (tab == NavTab.TRACKER) {
                // Ping circle
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 4.dp, y = (-2).dp)
                        .size(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(pingScale)
                            .background(Secondary.copy(alpha = pingAlpha), CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Secondary, CircleShape)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = tab.label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = color
            )
        )
    }
}
