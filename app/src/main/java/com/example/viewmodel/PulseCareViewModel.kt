package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PulseCareUiState(
    val currentTab: NavTab = NavTab.HOME,
    val isCheckedIn: Boolean = false,
    val queuePosition: Int = 3,
    val queueId: String = "#A-204",
    val estWaitMinutes: Int = 14,
    val toastMessage: String? = null,
    
    // Pre-visit checklist items
    val checklistItems: List<PreVisitChecklistItem> = listOf(
        PreVisitChecklistItem("checkin", "Digital Check-in completed", "Completed at 10:15 AM", isCompleted = true, isLocked = true),
        PreVisitChecklistItem("insurance", "Insurance verification approved", "Verified with BlueCross", isCompleted = true, isLocked = true),
        PreVisitChecklistItem("meds", "Confirm recent medication list", "Tap to review 4 prescriptions", isCompleted = false),
        PreVisitChecklistItem("symptoms", "Symptom questionnaire submitted", "2 quick questions remaining", isCompleted = false)
    ),
    
    // Booking filters & selected slots
    val selectedSpecialty: String = "Cardiology",
    val filterAvailableToday: Boolean = true,
    val filterInNetwork: Boolean = true,
    val filterTopRated: Boolean = false,
    val doctorSlotSelections: Map<String, Int> = mapOf(
        "vance" to 0,
        "thorne" to 0,
        "patel" to 0
    ),
    val bookingSuccessDoctorName: String? = null,
    
    // Records state
    val recordCategoryTab: String = "Visits",
    val recordYearFilter: String = "All Years",
    val isAudioPlaying: Boolean = false,
    val audioRemainingSeconds: Int = 105,
    val is2023ArchiveExpanded: Boolean = false,
    
    // Profile state
    val isQrModalVisible: Boolean = false,
    val smsRemindersEnabled: Boolean = true,
    val liveQueueUpdatesEnabled: Boolean = true,
    val refillAlertsEnabled: Boolean = true,
    val emailSummaryDeliveryEnabled: Boolean = false,
    val conditions: List<String> = listOf("Mild Asthma", "Penicillin Allergy", "Hypertension (Monitored)"),
    
    // Active dialogs
    val activeChatRecipient: String? = null,
    val activeCallRecipient: String? = null,
    val isRescheduleDialogVisible: Boolean = false,
    val isDirectionsDialogVisible: Boolean = false
)

class PulseCareViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PulseCareUiState())
    val uiState: StateFlow<PulseCareUiState> = _uiState.asStateFlow()

    private var audioTimerJob: Job? = null

    fun selectTab(tab: NavTab) {
        _uiState.value = _uiState.value.copy(currentTab = tab)
    }

    fun checkIn() {
        if (!_uiState.value.isCheckedIn) {
            _uiState.value = _uiState.value.copy(
                isCheckedIn = true,
                queuePosition = 4,
                toastMessage = "Welcome! Reception notified. Vitals kiosk 2 is ready."
            )
            // Auto dismiss toast after 3.5 seconds
            viewModelScope.launch {
                delay(3500)
                clearToast()
            }
        }
    }

    fun toggleChecklistItem(id: String) {
        val updated = _uiState.value.checklistItems.map { item ->
            if (item.id == id && !item.isLocked) {
                item.copy(isCompleted = !item.isCompleted)
            } else {
                item
            }
        }
        _uiState.value = _uiState.value.copy(checklistItems = updated)
    }

    fun selectSpecialty(specialty: String) {
        _uiState.value = _uiState.value.copy(selectedSpecialty = specialty)
    }

    fun toggleFilterAvailableToday() {
        _uiState.value = _uiState.value.copy(filterAvailableToday = !_uiState.value.filterAvailableToday)
    }

    fun toggleFilterInNetwork() {
        _uiState.value = _uiState.value.copy(filterInNetwork = !_uiState.value.filterInNetwork)
    }

    fun toggleFilterTopRated() {
        _uiState.value = _uiState.value.copy(filterTopRated = !_uiState.value.filterTopRated)
    }

    fun selectDoctorSlot(doctorId: String, slotIndex: Int) {
        val updated = _uiState.value.doctorSlotSelections.toMutableMap()
        updated[doctorId] = slotIndex
        _uiState.value = _uiState.value.copy(doctorSlotSelections = updated)
    }

    fun bookSlot(doctorName: String, slotText: String) {
        _uiState.value = _uiState.value.copy(
            bookingSuccessDoctorName = doctorName,
            toastMessage = "Appointment booked with $doctorName for $slotText!"
        )
        viewModelScope.launch {
            delay(3500)
            clearToast()
        }
    }

    fun dismissBookingConfirmation() {
        _uiState.value = _uiState.value.copy(bookingSuccessDoctorName = null)
    }

    fun setRecordTab(tab: String) {
        _uiState.value = _uiState.value.copy(recordCategoryTab = tab)
    }

    fun setRecordYear(year: String) {
        _uiState.value = _uiState.value.copy(recordYearFilter = year)
    }

    fun toggleAudioNote() {
        if (_uiState.value.isAudioPlaying) {
            audioTimerJob?.cancel()
            _uiState.value = _uiState.value.copy(isAudioPlaying = false)
        } else {
            _uiState.value = _uiState.value.copy(isAudioPlaying = true)
            audioTimerJob = viewModelScope.launch {
                while (_uiState.value.isAudioPlaying && _uiState.value.audioRemainingSeconds > 0) {
                    delay(1000)
                    val newSec = _uiState.value.audioRemainingSeconds - 1
                    if (newSec <= 0) {
                        _uiState.value = _uiState.value.copy(isAudioPlaying = false, audioRemainingSeconds = 105)
                        break
                    } else {
                        _uiState.value = _uiState.value.copy(audioRemainingSeconds = newSec)
                    }
                }
            }
        }
    }

    fun toggle2023Archive() {
        _uiState.value = _uiState.value.copy(is2023ArchiveExpanded = !_uiState.value.is2023ArchiveExpanded)
    }

    fun shareRecordLink() {
        _uiState.value = _uiState.value.copy(
            toastMessage = "Encrypted 72hr access link copied! (HIPAA Safe-Harbor)"
        )
        viewModelScope.launch {
            delay(3500)
            clearToast()
        }
    }

    fun toggleQrModal(visible: Boolean) {
        _uiState.value = _uiState.value.copy(isQrModalVisible = visible)
    }

    fun toggleSmsReminders(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(smsRemindersEnabled = enabled)
    }

    fun toggleLiveQueueUpdates(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(liveQueueUpdatesEnabled = enabled)
    }

    fun toggleRefillAlerts(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(refillAlertsEnabled = enabled)
    }

    fun toggleEmailSummary(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(emailSummaryDeliveryEnabled = enabled)
    }

    fun removeCondition(name: String) {
        val updated = _uiState.value.conditions.filter { it != name }
        _uiState.value = _uiState.value.copy(conditions = updated)
    }

    fun addCondition(name: String) {
        if (name.isNotBlank() && !_uiState.value.conditions.contains(name)) {
            _uiState.value = _uiState.value.copy(conditions = _uiState.value.conditions + name)
        }
    }

    fun openChat(recipient: String) {
        _uiState.value = _uiState.value.copy(activeChatRecipient = recipient)
    }

    fun closeChat() {
        _uiState.value = _uiState.value.copy(activeChatRecipient = null)
    }

    fun openCall(recipient: String) {
        _uiState.value = _uiState.value.copy(activeCallRecipient = recipient)
    }

    fun closeCall() {
        _uiState.value = _uiState.value.copy(activeCallRecipient = null)
    }

    fun showReschedule(show: Boolean) {
        _uiState.value = _uiState.value.copy(isRescheduleDialogVisible = show)
    }

    fun showDirections(show: Boolean) {
        _uiState.value = _uiState.value.copy(isDirectionsDialogVisible = show)
    }

    fun rescheduleAppointment(slot: String) {
        _uiState.value = _uiState.value.copy(
            isRescheduleDialogVisible = false,
            toastMessage = "Visit rescheduled to $slot. Confirmation sent!"
        )
        viewModelScope.launch {
            delay(3500)
            clearToast()
        }
    }

    fun clearToast() {
        _uiState.value = _uiState.value.copy(toastMessage = null)
    }

    fun dismissToast() = clearToast()
}
