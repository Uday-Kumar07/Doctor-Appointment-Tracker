package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*

@Composable
fun ToastNotificationBanner(
    message: String?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = message != null,
        enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { -it }),
        modifier = modifier
    ) {
        if (message != null) {
            Surface(
                color = InverseSurface,
                contentColor = InverseOnSurface,
                shape = RoundedCornerShape(14.dp),
                shadowElevation = 8.dp,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = InversePrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = InverseOnSurface
                            )
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Dismiss",
                            tint = InverseOnSurface.copy(alpha = 0.7f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatDialog(
    recipientName: String,
    onDismiss: () -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            "Hello Sarah! How can we assist you today?",
            "Your appointment check-in is all set with Dr. Vance."
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(TealAccent, CircleShape)
                )
                Text(
                    text = "Chat: $recipientName",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp, max = 320.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    messages.forEach { msg ->
                        Surface(
                            color = SurfaceContainerLow,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth(0.9f)
                        ) {
                            Text(
                                text = msg,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Type a message...", fontSize = 13.sp) },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (messageText.isNotBlank()) {
                                    messages.add(messageText)
                                    messageText = ""
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                tint = Primary
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = Primary)
            }
        }
    )
}

@Composable
fun CallDialog(
    recipientName: String,
    phoneNumber: String = "(555) 234-8901",
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(32.dp)
            )
        },
        title = {
            Text(
                text = "Contact $recipientName",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Direct clinical line:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = phoneNumber,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Liaison hours: Mon–Fri 8:00 AM – 5:30 PM",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Text("Dial Number")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun DirectionsDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.Directions,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(32.dp)
            )
        },
        title = {
            Text(
                text = "St. Jude Medical Pavilion",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Address: 8700 Beverly Blvd, Suite 304",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = "Parking Note: Structure B, Level 2 validated at front desk. Take Skybridge directly to Floor 3.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = "Current walking time from Structure B: 3 mins.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Primary
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Text("Open Google Maps")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun RescheduleDialog(
    onDismiss: () -> Unit,
    onRescheduled: (String) -> Unit
) {
    val options = listOf(
        "Tomorrow, 2:30 PM",
        "Friday, 11:00 AM",
        "Next Monday, 9:00 AM"
    )
    var selectedOption by remember { mutableStateOf(options[0]) }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.EditCalendar,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(32.dp)
            )
        },
        title = {
            Text(
                text = "Reschedule Cardiology Visit",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Select an alternate slot with Dr. Eleanor Vance:",
                    style = MaterialTheme.typography.bodyMedium
                )
                options.forEach { opt ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (selectedOption == opt) SecondaryContainer else SurfaceContainerLow)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = selectedOption == opt,
                            onClick = { selectedOption = opt }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = opt,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = if (selectedOption == opt) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedOption == opt) OnSecondaryContainer else OnSurface
                            )
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onRescheduled(selectedOption) },
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Text("Confirm Slot")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Keep Current")
            }
        }
    )
}

@Composable
fun NotificationsBottomSheet(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "PulseCare Notifications",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Surface(
                    color = SurfaceContainerLow,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Cardiology Visit in 2 Hours",
                            style = MaterialTheme.typography.labelLarge.copy(color = Primary)
                        )
                        Text(
                            text = "Pre-visit vitals kiosk is open at St. Jude Pavilion Suite 304.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    color = SurfaceContainerLow,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Lab Results Available",
                            style = MaterialTheme.typography.labelLarge.copy(color = OnSurface)
                        )
                        Text(
                            text = "Quest Diagnostics fasting lipid panel synched with Stanford Epic EMR.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = Primary)
            }
        }
    )
}
