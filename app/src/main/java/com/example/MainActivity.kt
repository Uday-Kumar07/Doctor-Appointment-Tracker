package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.NavTab
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.PulseCareTheme
import com.example.viewmodel.PulseCareViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PulseCareTheme {
                val viewModel: PulseCareViewModel = viewModel()
                PulseCareApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun PulseCareApp(
    viewModel: PulseCareViewModel
) {
    val state by viewModel.uiState.collectAsState()
    var showNotifications by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PulseCareTopBar(
                currentTab = state.currentTab,
                onNotificationClick = { showNotifications = true },
                onProfileClick = { viewModel.selectTab(NavTab.PROFILE) }
            )
        },
        bottomBar = {
            PulseCareBottomNav(
                currentTab = state.currentTab,
                onTabSelected = { tab -> viewModel.selectTab(tab) }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedContent(
                targetState = state.currentTab,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "screen_transition"
            ) { targetTab ->
                when (targetTab) {
                    NavTab.HOME -> HomeScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                    NavTab.BOOK -> BookScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                    NavTab.TRACKER -> TrackerScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                    NavTab.RECORDS -> RecordsScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                    NavTab.PROFILE -> ProfileScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }

            // Toast overlay on top
            ToastNotificationBanner(
                message = state.toastMessage,
                onDismiss = { viewModel.dismissToast() },
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(top = 8.dp)
            )
        }

        // Chat Modal
        state.activeChatRecipient?.let { recipient ->
            ChatDialog(
                recipientName = recipient,
                onDismiss = { viewModel.closeChat() }
            )
        }

        // Call Modal
        state.activeCallRecipient?.let { recipient ->
            CallDialog(
                recipientName = recipient,
                onDismiss = { viewModel.closeCall() }
            )
        }

        // Directions Modal
        if (state.isDirectionsDialogVisible) {
            DirectionsDialog(
                onDismiss = { viewModel.showDirections(false) }
            )
        }

        // Reschedule Modal
        if (state.isRescheduleDialogVisible) {
            RescheduleDialog(
                onDismiss = { viewModel.showReschedule(false) },
                onRescheduled = { slot ->
                    viewModel.rescheduleAppointment(slot)
                }
            )
        }

        // Notifications Modal
        if (showNotifications) {
            NotificationsBottomSheet(
                onDismiss = { showNotifications = false }
            )
        }
    }
}
