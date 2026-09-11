package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.PulseCareData
import com.example.ui.theme.*
import com.example.viewmodel.PulseCareViewModel

@Composable
fun ProfileScreen(
    viewModel: PulseCareViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    var showAddConditionDialog by remember { mutableStateOf(false) }
    var newConditionInput by remember { mutableStateOf("") }
    var showEditProfileDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Surface)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // 1. PATIENT IDENTITY HERO CARD
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("patient_profile_hero")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(76.dp)) {
                            AsyncImage(
                                model = PulseCareData.SARAH_PORTRAIT_URL,
                                contentDescription = "Sarah Jenkins",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(22.dp)
                                    .background(Primary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Verified Identity",
                                    tint = OnPrimary,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Sarah Jenkins",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = OnSurface,
                                        fontSize = 20.sp
                                    )
                                )
                                Surface(
                                    color = SurfaceContainerLow,
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clickable { showEditProfileDialog = true }
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit Profile",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            Text(
                                text = "32 yrs • Female • ID: #PC-849201",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.outline
                                )
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalHospital,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = "Cedars-Sinai Medical Center",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Primary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }

                    // Quick Vital Badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            color = SurfaceContainerLow,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(SecondaryContainer, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bloodtype,
                                        contentDescription = null,
                                        tint = OnSecondaryContainer,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Blood Type",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = MaterialTheme.colorScheme.outline,
                                            fontSize = 10.sp
                                        )
                                    )
                                    Text(
                                        text = "A Positive",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = OnSurface
                                        )
                                    )
                                }
                            }
                        }

                        Surface(
                            color = ErrorContainer,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(Error, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Emergency,
                                        contentDescription = null,
                                        tint = OnError,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Critical Alert",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = OnErrorContainer,
                                            fontSize = 10.sp
                                        )
                                    )
                                    Text(
                                        text = "Penicillin Allergy",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = OnErrorContainer
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. DIGITAL INSURANCE CARD SECTION
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.HealthAndSafety,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Digital Insurance",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        )
                    }

                    Surface(
                        color = SecondaryContainer,
                        shape = RoundedCornerShape(50)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .background(Primary, CircleShape)
                            )
                            Text(
                                text = "Verified 2024",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = OnSecondaryContainer,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                }

                // Realistic Digital Pass Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Primary,
                                        PrimaryContainer,
                                        Tertiary
                                    )
                                )
                            )
                            .padding(18.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            // Top Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column {
                                    Text(
                                        text = "HEALTH INSURANCE PASS",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = PrimaryFixed,
                                            letterSpacing = 1.sp,
                                            fontSize = 10.sp
                                        )
                                    )
                                    Text(
                                        text = "BlueCross BlueShield",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = OnPrimary
                                        )
                                    )
                                    Text(
                                        text = "Premier Plus Plan",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = PrimaryFixedDim
                                        )
                                    )
                                }

                                Surface(
                                    color = Color.White.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Contactless,
                                            contentDescription = "Contactless Scan",
                                            tint = OnPrimary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }

                            // Policy & Group Numbers
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "Member Policy",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = PrimaryFixed,
                                            fontSize = 10.sp
                                        )
                                    )
                                    Text(
                                        text = "BC-908234-91",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace,
                                            color = OnPrimary
                                        )
                                    )
                                }

                                Column {
                                    Text(
                                        text = "Group Number",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = PrimaryFixed,
                                            fontSize = 10.sp
                                        )
                                    )
                                    Text(
                                        text = "GRP-4412",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace,
                                            color = OnPrimary
                                        )
                                    )
                                }
                            }

                            // Copays Strip
                            HorizontalDivider(color = Color.White.copy(alpha = 0.2f))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Primary",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = PrimaryFixedDim,
                                            fontSize = 10.sp
                                        )
                                    )
                                    Text(
                                        text = "$20",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = OnPrimary
                                        )
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .height(24.dp)
                                        .background(Color.White.copy(alpha = 0.2f))
                                )

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Specialist",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = PrimaryFixedDim,
                                            fontSize = 10.sp
                                        )
                                    )
                                    Text(
                                        text = "$35",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = OnPrimary
                                        )
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .height(24.dp)
                                        .background(Color.White.copy(alpha = 0.2f))
                                )

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Urgent Care",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = PrimaryFixedDim,
                                            fontSize = 10.sp
                                        )
                                    )
                                    Text(
                                        text = "$50",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = OnPrimary
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                // Card Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { viewModel.toggleQrModal(!state.isQrModalVisible) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SurfaceContainerLow,
                            contentColor = Primary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("show_member_qr_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode2,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (state.isQrModalVisible) "Hide QR Pass" else "Show Member QR",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Button(
                        onClick = { viewModel.shareRecordLink() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SurfaceContainerLow,
                            contentColor = Primary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Download Card",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                // Member QR Pass Modal / Card
                AnimatedVisibility(visible = state.isQrModalVisible) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Member Pass QR • Front Desk Scan",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = OnSurface
                                    )
                                )
                                IconButton(
                                    onClick = { viewModel.toggleQrModal(false) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close",
                                        tint = MaterialTheme.colorScheme.outline
                                    )
                                }
                            }

                            // Crisp QR Pattern
                            Surface(
                                color = SurfaceContainerLow,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.size(160.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    SimulatedQrCode(modifier = Modifier.size(130.dp))
                                }
                            }

                            Text(
                                text = "Touchless check-in valid at all Cedars-Sinai & in-network kiosks",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.outline,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        // 3. CONDITIONS & ALLERGIES
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MedicalInformation,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Conditions & Allergies",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                            )
                        }

                        Text(
                            text = "${state.conditions.size} Recorded",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.outline
                            )
                        )
                    }

                    // Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        state.conditions.forEach { cond ->
                            val isAllergy = cond.contains("Allergy", ignoreCase = true)
                            Surface(
                                color = if (isAllergy) ErrorContainer else SurfaceContainer,
                                shape = RoundedCornerShape(50),
                                modifier = Modifier.padding(bottom = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .background(
                                                if (isAllergy) Error else Secondary,
                                                CircleShape
                                            )
                                    )
                                    Text(
                                        text = cond,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = if (isAllergy) OnErrorContainer else OnSurface,
                                            fontWeight = if (isAllergy) FontWeight.Bold else FontWeight.Medium
                                        )
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Remove",
                                        tint = if (isAllergy) OnErrorContainer else MaterialTheme.colorScheme.outline,
                                        modifier = Modifier
                                            .size(14.dp)
                                            .clickable { viewModel.removeCondition(cond) }
                                    )
                                }
                            }
                        }
                    }

                    // Add Condition Button
                    Button(
                        onClick = { showAddConditionDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SurfaceContainerLow,
                            contentColor = Primary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Add Condition / Allergy",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                        )
                    }
                }
            }
        }

        // 4. EMERGENCY CONTACTS
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContactEmergency,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Emergency Contacts",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                            )
                        }

                        Text(
                            text = "+ New",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Primary,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.clickable {
                                viewModel.openChat("Emergency Care Coordinator")
                            }
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        PulseCareData.emergencyContacts.forEach { contact ->
                            Surface(
                                color = SurfaceContainerLow,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(38.dp)
                                                .background(
                                                    if (contact.isSpouse) Primary.copy(alpha = 0.1f) else SecondaryContainer,
                                                    CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = if (contact.isSpouse) Icons.Default.Favorite else Icons.Default.MedicalServices,
                                                contentDescription = null,
                                                tint = if (contact.isSpouse) Primary else OnSecondaryContainer,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }

                                        Column {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                                            ) {
                                                Text(
                                                    text = contact.name,
                                                    style = MaterialTheme.typography.labelMedium.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        color = OnSurface
                                                    )
                                                )
                                                Text(
                                                    text = contact.relation,
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        color = MaterialTheme.colorScheme.outline
                                                    )
                                                )
                                            }
                                            Text(
                                                text = contact.phone,
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    color = MaterialTheme.colorScheme.outline,
                                                    fontSize = 11.sp
                                                )
                                            )
                                        }
                                    }

                                    Surface(
                                        color = SurfaceContainer,
                                        shape = CircleShape,
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable { viewModel.openCall(contact.name) }
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.Call,
                                                contentDescription = "Call Contact",
                                                tint = Primary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 5. REMINDER PREFERENCES
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Reminder Preferences",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        PreferenceToggleRow(
                            title = "SMS Reminders (24h before)",
                            subtitle = "Receive text verification to arrive on time",
                            checked = state.smsRemindersEnabled,
                            onCheckedChange = { viewModel.toggleSmsReminders(it) }
                        )

                        PreferenceToggleRow(
                            title = "Live Queue Wait Updates",
                            subtitle = "Real-time alert when 2 patients ahead",
                            checked = state.liveQueueUpdatesEnabled,
                            onCheckedChange = { viewModel.toggleLiveQueueUpdates(it) }
                        )

                        PreferenceToggleRow(
                            title = "Prescription Refill Alerts",
                            subtitle = "Pharmacy auto-request warnings",
                            checked = state.refillAlertsEnabled,
                            onCheckedChange = { viewModel.toggleRefillAlerts(it) }
                        )

                        PreferenceToggleRow(
                            title = "Email Summary Delivery",
                            subtitle = "After-visit notes & lab results via PDF",
                            checked = state.emailSummaryDeliveryEnabled,
                            onCheckedChange = { viewModel.toggleEmailSummary(it) }
                        )
                    }
                }
            }
        }

        // 6. ACCOUNT & HIPAA PRIVACY
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Account & HIPAA Privacy",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // HIPAA Consent
                        Surface(
                            color = SurfaceContainerLow,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .background(SecondaryContainer, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.LockPerson,
                                            contentDescription = null,
                                            tint = OnSecondaryContainer,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = "HIPAA Data Sharing Consent",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = OnSurface
                                            )
                                        )
                                        Text(
                                            text = "Signed Oct 14, 2023 • Encrypted",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = MaterialTheme.colorScheme.outline
                                            )
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified",
                                    tint = Primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // 2FA
                        Surface(
                            color = SurfaceContainerLow,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .background(Primary.copy(alpha = 0.1f), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.PhonelinkLock,
                                            contentDescription = null,
                                            tint = Primary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = "Two-Factor Authentication",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = OnSurface
                                            )
                                        )
                                        Text(
                                            text = "SMS to ***-***-5678 active",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = MaterialTheme.colorScheme.outline
                                            )
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // Biometric FaceID
                        Surface(
                            color = SurfaceContainerLow,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .background(Primary.copy(alpha = 0.1f), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Face,
                                            contentDescription = null,
                                            tint = Primary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = "Biometric FaceID",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = OnSurface
                                            )
                                        )
                                        Text(
                                            text = "Enabled for instant check-in",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = MaterialTheme.colorScheme.outline
                                            )
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Active",
                                    tint = Primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    // Logout Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier
                                .clickable {
                                    viewModel.shareRecordLink()
                                }
                                .padding(vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = null,
                                tint = Error,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Sign Out of PulseCare Account",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = Error,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    // Add Condition Dialog
    if (showAddConditionDialog) {
        AlertDialog(
            onDismissRequest = { showAddConditionDialog = false },
            title = {
                Text(
                    text = "Add Condition or Allergy",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Enter the medical diagnosis or allergen:",
                        style = MaterialTheme.typography.bodySmall
                    )
                    OutlinedTextField(
                        value = newConditionInput,
                        onValueChange = { newConditionInput = it },
                        placeholder = { Text("e.g. Seasonal Pollen Allergy") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newConditionInput.isNotBlank()) {
                            viewModel.addCondition(newConditionInput.trim())
                            newConditionInput = ""
                            showAddConditionDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddConditionDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Edit Profile Dialog
    if (showEditProfileDialog) {
        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = {
                Text(
                    text = "Personal Details",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Name: Sarah Jenkins", style = MaterialTheme.typography.bodyMedium)
                    Text("DOB: May 12, 1992 (32 yrs)", style = MaterialTheme.typography.bodyMedium)
                    Text("Primary Hospital: Cedars-Sinai", style = MaterialTheme.typography.bodyMedium)
                    Text("Blood Type: A Positive", style = MaterialTheme.typography.bodyMedium)
                }
            },
            confirmButton = {
                Button(
                    onClick = { showEditProfileDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("Done")
                }
            }
        )
    }
}

@Composable
fun PreferenceToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurface
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = OnPrimary,
                checkedTrackColor = Primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = SurfaceContainerHighest
            )
        )
    }
}

@Composable
fun SimulatedQrCode(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val cellSize = size.width / 11f
        // Draw standard QR corner squares and data matrix dots
        val black = Color(0xFF131B2E)

        fun drawSquare(col: Int, row: Int, s: Int) {
            drawRect(
                color = black,
                topLeft = Offset(col * cellSize, row * cellSize),
                size = Size(s * cellSize, s * cellSize)
            )
        }

        // Top-left finder pattern
        drawSquare(0, 0, 3)
        drawSquare(1, 1, 1)

        // Top-right finder pattern
        drawSquare(8, 0, 3)
        drawSquare(9, 1, 1)

        // Bottom-left finder pattern
        drawSquare(0, 8, 3)
        drawSquare(1, 9, 1)

        // Random matrix dots simulating high density QR
        val dots = listOf(
            4 to 0, 5 to 0, 4 to 2, 5 to 3, 6 to 3, 4 to 5, 5 to 5, 6 to 5,
            1 to 4, 2 to 4, 3 to 4, 8 to 4, 9 to 4, 10 to 4,
            4 to 7, 5 to 8, 6 to 9, 7 to 7, 8 to 8, 9 to 9, 10 to 8,
            7 to 1, 7 to 2, 4 to 10, 6 to 10, 8 to 10
        )
        dots.forEach { (c, r) ->
            drawRect(
                color = black,
                topLeft = Offset(c * cellSize, r * cellSize),
                size = Size(cellSize * 0.9f, cellSize * 0.9f)
            )
        }
    }
}
