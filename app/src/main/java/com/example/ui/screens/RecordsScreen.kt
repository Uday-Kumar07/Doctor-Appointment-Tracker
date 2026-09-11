package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
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
import com.example.model.CareRecord
import com.example.model.PulseCareData
import com.example.ui.theme.*
import com.example.viewmodel.PulseCareViewModel

@Composable
fun RecordsScreen(
    viewModel: PulseCareViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    var searchRecordQuery by remember { mutableStateOf("") }

    val filterChips = listOf("All Years", "2024", "2023", "Specialists", "Flagged Findings")
    val tabs = listOf(
        "Visits" to 8,
        "Scripts" to 4,
        "Labs" to 6,
        "Notes" to 3
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Surface)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Header with HIPAA Encrypted Pill
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Care Records & History",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface,
                                fontSize = 18.sp
                            )
                        )
                        Text(
                            text = "Verified clinical documentation & encounters",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.outline
                            )
                        )
                    }

                    Surface(
                        color = SecondaryContainer,
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.clickable { viewModel.shareRecordLink() }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = OnSecondaryContainer,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "HIPAA Encrypted",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = OnSecondaryContainer,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }

                // Search Bar
                Surface(
                    color = SurfaceContainerLow,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 2.dp),
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
                            value = searchRecordQuery,
                            onValueChange = { searchRecordQuery = it },
                            placeholder = {
                                Text(
                                    text = "Search visits, medications, or doctors...",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.outline,
                                        fontSize = 13.sp
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
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { /* Voice search */ }) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "Voice Search",
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // Filter Pills Row
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(filterChips) { chip ->
                        val isSelected = state.recordYearFilter == chip
                        Surface(
                            color = if (isSelected) Primary else SurfaceContainer,
                            contentColor = if (isSelected) OnPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            shape = RoundedCornerShape(50),
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .clickable { viewModel.setRecordYear(chip) }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = chip,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                )
                                if (chip == "All Years") {
                                    Icon(
                                        imageVector = Icons.Default.ExpandMore,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Segmented Tabs
        item {
            Surface(
                color = SurfaceContainerLow,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    tabs.forEach { (title, count) ->
                        val isSelected = state.recordCategoryTab == title
                        Surface(
                            color = if (isSelected) SurfaceContainerLowest else Color.Transparent,
                            shape = RoundedCornerShape(8.dp),
                            shadowElevation = if (isSelected) 1.dp else 0.dp,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { viewModel.setRecordTab(title) }
                                .testTag("record_tab_$title")
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                                Text(
                                    text = "($count)",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        color = if (isSelected) Primary.copy(alpha = 0.8f) else MaterialTheme.colorScheme.outline
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Security & Sync Micro-Bar
        item {
            Surface(
                color = SurfaceContainerLow,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(Primary, CircleShape)
                        )
                        Text(
                            text = "Synched with Stanford Health Epic EMR",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    Text(
                        text = "Live",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        // 4. Timeline Cards
        items(PulseCareData.careRecords) { record ->
            CareRecordCard(
                record = record,
                isAudioPlaying = state.isAudioPlaying,
                audioRemainingSeconds = state.audioRemainingSeconds,
                onAudioToggle = { viewModel.toggleAudioNote() },
                onViewPdf = { viewModel.shareRecordLink() },
                onPrescriptions = { viewModel.shareRecordLink() }
            )
        }

        // 5. Divider
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                HorizontalDivider(modifier = Modifier.width(36.dp), color = SurfaceContainerHighest)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "End of 2024 Records",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 11.sp
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                HorizontalDivider(modifier = Modifier.width(36.dp), color = SurfaceContainerHighest)
            }
        }

        // 6. Collapsible Archive Preview for 2023
        item {
            Surface(
                color = SurfaceContainerLow,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FolderSpecial,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(22.dp)
                            )
                            Column {
                                Text(
                                    text = "5 Encounters in 2023",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = OnSurface
                                    )
                                )
                                Text(
                                    text = "Immunizations, Dental, & Urgent Care",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.outline,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        Surface(
                            color = SurfaceContainer,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { viewModel.toggle2023Archive() }
                        ) {
                            Text(
                                text = if (state.is2023ArchiveExpanded) "Collapse" else "Expand",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Primary,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    AnimatedVisibility(visible = state.is2023ArchiveExpanded) {
                        Column(
                            modifier = Modifier.padding(top = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "• Dec 14, 2023: Annual Flu Vaccine (St. Jude Pavilion)",
                                style = MaterialTheme.typography.bodySmall.copy(color = OnSurface)
                            )
                            Text(
                                text = "• Oct 02, 2023: Dental Cleaning & Bitewing X-Rays",
                                style = MaterialTheme.typography.bodySmall.copy(color = OnSurface)
                            )
                            Text(
                                text = "• Jun 18, 2023: Urgent Care Sprained Ankle Assessment",
                                style = MaterialTheme.typography.bodySmall.copy(color = OnSurface)
                            )
                        }
                    }
                }
            }
        }

        // 7. Sticky/Bottom Sharing Action Card
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
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Record Sharing & Portability",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                            )
                        }
                        Text(
                            text = "End-to-End Secure",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Secondary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.shareRecordLink() },
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("share_with_doctor_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddLink,
                                contentDescription = null,
                                tint = OnPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Share with Doctor",
                                style = MaterialTheme.typography.labelMedium.copy(color = OnPrimary)
                            )
                        }

                        Button(
                            onClick = { viewModel.shareRecordLink() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SurfaceContainer,
                                contentColor = OnSurface
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoveToInbox,
                                contentDescription = null,
                                tint = OnSurface,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Transfer Records",
                                style = MaterialTheme.typography.labelMedium.copy(color = OnSurface)
                            )
                        }
                    }

                    Text(
                        text = "Export generated links expire automatically after 72 hours under HIPAA safe-harbor rule.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.outline,
                            fontSize = 11.sp
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun CareRecordCard(
    record: CareRecord,
    isAudioPlaying: Boolean,
    audioRemainingSeconds: Int,
    onAudioToggle: () -> Unit,
    onViewPdf: () -> Unit,
    onPrescriptions: () -> Unit
) {
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
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    if (record.isLab) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(PrimaryContainer, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Biotech,
                                contentDescription = null,
                                tint = OnPrimaryContainer,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else if (record.avatarUrl != null) {
                        Box(modifier = Modifier.size(44.dp)) {
                            AsyncImage(
                                model = record.avatarUrl,
                                contentDescription = record.provider,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = record.provider,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                            )
                            if (!record.isLab) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Verified",
                                    tint = Primary,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                        Text(
                            text = record.clinicOrType,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.outline,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Surface(
                        color = if (record.id == "rec1") SecondaryContainer else SurfaceContainer,
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            text = record.dateText,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = if (record.id == "rec1") OnSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = record.durationOrTag,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.outline,
                            fontSize = 10.sp
                        ),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Title & Description
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = record.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                )
                Text(
                    text = record.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            // Clinical Outcome or Flagged Marker Box
            Surface(
                color = if (record.isFlagged) ErrorContainer.copy(alpha = 0.5f) else SurfaceContainerLow,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
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
                                imageVector = if (record.isFlagged) Icons.Default.Flag else Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = if (record.isFlagged) Error else Primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = record.outcomeLabel,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.outline,
                                    fontSize = 10.sp,
                                    letterSpacing = 0.5.sp
                                )
                            )
                        }

                        if (record.isFlagged && record.flaggedText != null) {
                            Surface(
                                color = ErrorContainer,
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    text = record.flaggedText,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = OnErrorContainer,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = record.outcomeText,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = OnSurface
                        )
                    )
                }
            }

            // Doctor's Voice Note Player (Card 1)
            if (record.hasAudioNote) {
                Surface(
                    color = SecondaryContainer.copy(alpha = 0.35f),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val audioBtnColor by animateColorAsState(
                            targetValue = if (isAudioPlaying) Secondary else Primary,
                            label = "audioBtnColor"
                        )

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(audioBtnColor, CircleShape)
                                .clickable(onClick = onAudioToggle),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isAudioPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Play Audio",
                                tint = OnPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Mic,
                                        contentDescription = null,
                                        tint = Primary,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Text(
                                        text = "Doctor's Audio Note",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = OnSecondaryContainer
                                        )
                                    )
                                }
                                val mins = audioRemainingSeconds / 60
                                val secs = audioRemainingSeconds % 60
                                Text(
                                    text = String.format("%d:%02d", mins, secs),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            // Dynamic Waveform bars
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val barHeights = listOf(8, 14, 11, 7, 14, 9, 6, 12, 14, 8, 10, 6, 9)
                                barHeights.forEachIndexed { idx, height ->
                                    val barColor = if (idx % 2 == 0) Primary else Primary.copy(alpha = 0.5f)
                                    Box(
                                        modifier = Modifier
                                            .width(4.dp)
                                            .height(height.dp)
                                            .background(barColor, RoundedCornerShape(2.dp))
                                    )
                                }
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.GraphicEq,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Action Buttons
            if (record.isLab) {
                Button(
                    onClick = onViewPdf,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryContainer,
                        contentColor = OnSecondaryContainer
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
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
                                imageVector = Icons.Default.DownloadForOffline,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Download Lab Report PDF",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Text(
                            text = "1.4 MB",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnSecondaryContainer.copy(alpha = 0.8f)
                            )
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onViewPdf,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (record.id == "rec1") Primary else SurfaceContainer,
                            contentColor = if (record.id == "rec1") OnPrimary else Primary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {
                        Icon(
                            imageVector = if (record.id == "rec1") Icons.Default.PictureAsPdf else Icons.Default.Visibility,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = record.pdfName ?: "View Summary",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                    Button(
                        onClick = onPrescriptions,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SurfaceContainer,
                            contentColor = if (record.id == "rec1") Primary else MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {
                        Icon(
                            imageVector = if (record.id == "rec1") Icons.Default.Medication else Icons.Default.ReceiptLong,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (record.id == "rec1") "Prescriptions (2)" else "Receipt & Claim",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}
