package com.example.model

enum class NavTab(val label: String, val subtitle: String) {
    HOME("Home", "Upcoming Visits"),
    BOOK("Book", "Find And Book Doctor"),
    TRACKER("Tracker", "Active Appointment Live Tracker"),
    RECORDS("Records", "Care Records"),
    PROFILE("Profile", "Patient Profile")
}

data class Doctor(
    val id: String,
    val name: String,
    val title: String,
    val specialty: String,
    val hospital: String,
    val rating: Double,
    val reviewsCount: Int,
    val experienceYears: Int,
    val copayText: String,
    val nextSlotText: String,
    val hasInPerson: Boolean,
    val hasVideoConsult: Boolean,
    val isTelehealthReady: Boolean = false,
    val photoUrl: String,
    val isVerified: Boolean = true,
    val badge: String? = null,
    val isTopChoice: Boolean = false,
    val availableSlots: List<String>,
    val selectedSlotIndex: Int = 0
)

data class HealthVital(
    val id: String,
    val name: String,
    val value: String,
    val unit: String,
    val statusLabel: String,
    val statusType: VitalStatusType
)

enum class VitalStatusType {
    NORMAL, STEADY, FASTING, ATTENTION
}

data class ScheduleItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val dateText: String,
    val iconType: ScheduleIconType
)

enum class ScheduleIconType {
    DENTAL, LAB
}

data class CareCircleMember(
    val id: String,
    val name: String,
    val role: String,
    val photoUrl: String,
    val phone: String
)

data class PreVisitChecklistItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val isCompleted: Boolean,
    val isLocked: Boolean = false
)

data class CareRecord(
    val id: String,
    val provider: String,
    val clinicOrType: String,
    val dateText: String,
    val durationOrTag: String,
    val title: String,
    val description: String,
    val outcomeLabel: String,
    val outcomeText: String,
    val hasAudioNote: Boolean = false,
    val audioDurationSeconds: Int = 105,
    val pdfName: String? = null,
    val prescriptionsCount: Int = 0,
    val isFlagged: Boolean = false,
    val flaggedText: String? = null,
    val isLab: Boolean = false,
    val avatarUrl: String? = null
)

data class EmergencyContact(
    val id: String,
    val name: String,
    val relation: String,
    val phone: String,
    val isSpouse: Boolean = false
)
