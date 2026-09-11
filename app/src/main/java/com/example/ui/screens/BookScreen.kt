package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.Doctor
import com.example.model.PulseCareData
import com.example.ui.theme.*
import com.example.viewmodel.PulseCareViewModel

@Composable
fun BookScreen(
    viewModel: PulseCareViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val specialties = listOf("All Specialties", "Cardiology", "Dermatology", "Neurology", "Pediatrics", "Orthopedics")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Surface)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Header & Title
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "VERIFIED SPECIALISTS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Primary,
                            letterSpacing = 0.8.sp
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(Secondary, CircleShape)
                        )
                        Text(
                            text = "34 Available Today",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Tertiary
                            )
                        )
                    }
                }

                Text(
                    text = "Find your care team",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        fontSize = 24.sp
                    )
                )

                Text(
                    text = "Direct booking with top-tier practitioners & immediate queue check-in.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }

        // 2. Search Box
        item {
            Surface(
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(14.dp),
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                text = "Search doctors, specialty, hospital...",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.outline
                                )
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("doctor_search_input")
                    )
                    Surface(
                        color = SurfaceContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { /* Toggle filter */ }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Filters",
                                tint = Primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        // 3. Specialties Row
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(specialties) { spec ->
                    val isSelected = state.selectedSpecialty == spec
                    Surface(
                        color = if (isSelected) Primary else SurfaceContainer,
                        contentColor = if (isSelected) OnPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = RoundedCornerShape(50),
                        shadowElevation = if (isSelected) 1.dp else 0.dp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .clickable { viewModel.selectSpecialty(spec) }
                            .testTag("specialty_chip_$spec")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            if (spec == "Cardiology") {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Text(
                                text = spec,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }
        }

        // 4. Filter Toggle Pills
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Available Today
                FilterPill(
                    label = "Available Today",
                    icon = Icons.Default.Bolt,
                    isActive = state.filterAvailableToday,
                    onClick = { viewModel.toggleFilterAvailableToday() }
                )

                // In-Network Insurance
                FilterPill(
                    label = "In-Network Insurance",
                    icon = Icons.Default.VerifiedUser,
                    isActive = state.filterInNetwork,
                    onClick = { viewModel.toggleFilterInNetwork() }
                )

                // Top Rated (4.8+)
                FilterPill(
                    label = "Top Rated (4.8+)",
                    icon = Icons.Default.Star,
                    isActive = state.filterTopRated,
                    onClick = { viewModel.toggleFilterTopRated() }
                )
            }
        }

        // 5. Doctor Cards List
        items(PulseCareData.doctors) { doctor ->
            val isMatch = (state.selectedSpecialty == "All Specialties" || doctor.specialty.contains(state.selectedSpecialty, ignoreCase = true) || state.selectedSpecialty == "Cardiology")
            if (isMatch) {
                DoctorCard(
                    doctor = doctor,
                    selectedSlotIndex = state.doctorSlotSelections[doctor.id] ?: 0,
                    onSlotSelected = { idx -> viewModel.selectDoctorSlot(doctor.id, idx) },
                    onBookClick = { slotText ->
                        viewModel.bookSlot(doctor.name, slotText)
                    },
                    onInfoClick = {
                        viewModel.openChat(doctor.name)
                    }
                )
            }
        }

        // 6. Specialist Help Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(PrimaryContainer, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SupportAgent,
                            contentDescription = null,
                            tint = OnPrimaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Need help choosing a specialist?",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        )
                        Text(
                            text = "Our clinical care coordinator can match your symptoms in 2 minutes.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }

                    Surface(
                        color = SurfaceContainerLowest,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { viewModel.openChat("Clinical Coordinator") }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = "Chat",
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

@Composable
fun FilterPill(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Surface(
        color = if (isActive) SecondaryContainer else SurfaceContainerLow,
        contentColor = if (isActive) OnSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
        shape = RoundedCornerShape(8.dp),
        shadowElevation = if (isActive) 1.dp else 0.dp,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun DoctorCard(
    doctor: Doctor,
    selectedSlotIndex: Int,
    onSlotSelected: (Int) -> Unit,
    onBookClick: (String) -> Unit,
    onInfoClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("doctor_card_${doctor.id}")
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Chief badge if available
            if (doctor.badge != null) {
                Surface(
                    color = SecondaryContainer,
                    shape = RoundedCornerShape(bottomStart = 12.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = OnSecondaryContainer,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = doctor.badge,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnSecondaryContainer,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Doctor info row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(60.dp)) {
                        AsyncImage(
                            model = doctor.photoUrl,
                            contentDescription = doctor.name,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                        if (doctor.isVerified) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .offset(x = 4.dp, y = 4.dp)
                                    .size(18.dp)
                                    .background(Primary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Verified",
                                    tint = OnPrimary,
                                    modifier = Modifier.size(11.dp)
                                )
                            }
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = doctor.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        )
                        Text(
                            text = if (doctor.id == "vance") doctor.hospital else doctor.title,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = StarYellow,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "${doctor.rating}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Primary
                                )
                            )
                            Text(
                                text = "• ${doctor.reviewsCount} reviews • ${doctor.experienceYears} yrs exp",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            )
                            if (doctor.isTopChoice) {
                                Text(
                                    text = "• Top Choice",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Secondary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }
                }

                // Coverage & Next Slot Strip (if present)
                if (doctor.id == "vance") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceContainerLow, RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Column {
                                Text(
                                    text = "Coverage",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MaterialTheme.colorScheme.outline,
                                        fontSize = 10.sp
                                    )
                                )
                                Text(
                                    text = "$35 Copay (In-Net)",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = OnSurface,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = Secondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Column {
                                Text(
                                    text = "Next Slot",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MaterialTheme.colorScheme.outline,
                                        fontSize = 10.sp
                                    )
                                )
                                Text(
                                    text = doctor.nextSlotText,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                } else if (doctor.id == "thorne") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceContainerLow, RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = Secondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Next: Tomorrow, 11:00 AM",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                            )
                        }
                        Surface(
                            color = SurfaceContainer,
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "In-Clinic",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                // Consultation tags row
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (doctor.isTelehealthReady) {
                        Surface(
                            color = SecondaryContainer,
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "📹 Telehealth Ready",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = OnSecondaryContainer,
                                    fontSize = 11.sp
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                    if (doctor.hasInPerson) {
                        Surface(
                            color = SurfaceContainer,
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "🏥 In-Person",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                    if (doctor.hasVideoConsult && !doctor.isTelehealthReady) {
                        Surface(
                            color = SecondaryFixed,
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "📹 Video Consult",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = OnSecondaryFixed,
                                    fontSize = 11.sp
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                // Slot selection grid
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Select appointment window",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.outline
                            )
                        )
                        Text(
                            text = "View calendar",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        doctor.availableSlots.forEachIndexed { index, slot ->
                            val isSlotSelected = index == selectedSlotIndex
                            val parts = slot.split(" ", limit = 2)
                            val dayText = parts.getOrNull(0) ?: "TODAY"
                            val timeText = parts.getOrNull(1) ?: slot

                            Surface(
                                color = if (isSlotSelected) PrimaryFixed else SurfaceContainerLow,
                                contentColor = if (isSlotSelected) OnPrimaryFixed else OnSurface,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onSlotSelected(index) }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = dayText,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 9.sp,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    )
                                    Text(
                                        text = timeText,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                // Booking action row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (doctor.id != "vance") {
                        Surface(
                            color = SurfaceContainer,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .size(44.dp)
                                .clickable(onClick = onInfoClick)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Doctor Info",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            val slot = doctor.availableSlots.getOrElse(selectedSlotIndex) { "Selected slot" }
                            onBookClick(slot)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("book_button_${doctor.id}")
                    ) {
                        Text(
                            text = if (doctor.id == "vance") "Book Slot with Dr. Vance" else "Book Slot",
                            style = MaterialTheme.typography.labelLarge.copy(color = OnPrimary)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = OnPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
