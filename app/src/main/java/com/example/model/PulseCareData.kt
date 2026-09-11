package com.example.model

object PulseCareData {
    const val LOGO_URL = "https://lh3.googleusercontent.com/aida/AEtjO1XWNHOUcB53FplpUvUGyzsIANxoA8xx8CIAFitDsAhaMee5RjYc5uh08RB3AQWJSPXO0CYnNH-sWlX_7Uhr2LIh9lU6BKwznNQntx2n8IibfOo2ICzafnO_82qRZJ0mlzqdRi3Ms7XAo9mSYnaiKbn3PXxvr95JMBiHp4SNxsWTFpwG1p53TCWdLuLKROLjfx_FTSQE5E8YBpqrkFKCPSuViDvDWcog-uAtare328sAZ9_opOxW3uoMU8Fb"
    const val PATIENT_AVATAR_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuCZySJtMhUEBNgkVO_xRSfgOTHxdoih1ftWMI3k3DrKT5ONLc4AFCX_Qnv__9GEtTjwivWUnMo1rFQVodd1xia9wGaTLQ9CbmR_athNU6IGo4gh06Ey8weCtdvebDpJjznoTK20L6sR5xILfrMROP3_v55iNhsV_UXEhFSLRIllZXEkAFT8Ec2sDysefmFVBbdyeDH-vv1dycw91jEU6EI_qGvRsvVwK-rc4G8uyx-vMouSOpaIC7eiZw"
    const val SARAH_PORTRAIT_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuBvqL5wCK-ZyKgXDRqPESYwP4-5o8-tCp7rJZyo-Ks3XwoWiMFZ8qIO6zGDhEkialI4IblX7Zs9OfJQlLtui4oRCHe2jHuaiTV895CjwU_XJgmKzeXkTz1WSN3XdTzcdVXK2zXxi0PQRR_CvtWL7pq8C-kSZy4RN4UH8QJFlSfkPCjOTA2AnWi-8PqvWLSy6g8Dp8ukHSW-WomGw4aGD3bQuTu3nu3kG7ehealOjM06Z2My7CZf6iz1iQ"
    
    const val DR_VANCE_AVATAR = "https://lh3.googleusercontent.com/aida-public/AB6AXuAOtIQcgEQvKctktY6jRPXjxUx1MonZdocvzQmSuvdzTkEuOolHxATHZ04PF4HK_XnFj80tq_V8yu19kMpiQR_tHTE_yJt2iLYdILphdIdY-M1es1QVr1PoOfYj5fOq3J8wkSoBJ6rP6UcMr1g395PhpCbVSnfiEzwttmz39HMJb7h3DbdA71-pAlJWb8rRe2bbnnwav4c0yXyEEnIxQs9DHdnlmk704qpRQScuh2y22KBBPXo6OsMW3A"
    const val DR_THORNE_AVATAR = "https://lh3.googleusercontent.com/aida-public/AB6AXuB0fPHCOaOv8M-sJIBFSC3vPyfKNxSe1d-YBxGGAiQKykOW01WmcO7Z_xUhLSYsY5qwTsFMChfoSx4VjvCj1k8RvTNFqA2QifKlLk-j41Xro8gxWlJh9e_YGidrKUyOKtN71t1j5Qn9wa87YDQbtvJUSOaiIONgfTZGG-v8EueaNC6C2LifbhnBtqDTJFCe_6soeVUjq7KJoOYgGHlVYfWv3hxOygHUyU_rPg-Vgtn_12OyFNQPCmuM5w"
    const val NURSE_MAYA_AVATAR = "https://lh3.googleusercontent.com/aida-public/AB6AXuCv4OO14mN-m4T86mGVfxVH_KCO6QWEvN54fczuFqw2oKAUZXvbTX7nGNS-IBMCONIL0IEhD78_FHzwT5eQv-XYpBfAJlM-toA4Wvbe3cKD83d5sviBq62wCW7xhdXPSVsTGEeKV4i3uWvvVUEIZRsiUlm6EFvOLIr_zkzohnwvk_gsnvqexP3P86yZRI_FR0FFiMBHFC_cm8FrujuOFvbccBavewnAcyvtpSEpl4WfDAcR-DK9SQn8KA"
    const val DR_PATEL_AVATAR = "https://lh3.googleusercontent.com/aida-public/AB6AXuAMRLAYUAIKhAINx4TkTutf7JDQw72DZsTPuGwc9oQeob586dfVdmUKUa-MufjoIF46mbkgF1V6i2IlEUJcOqPo_yJ65HTOs2ghVeWEpd_7JDDl3T0Sb2BvmS5zqugEn8CnKaoSyanD4vhRApoeJOiVTBdFBeHu33BEAvkHwm7JNNUqTttQJ768h67KYLx7Bm1Gkcvzt6OUHwgBXUz7vSjn5ucqI2yOO0ipFEjoQmr0sp6wTOUHCUCuDg"

    val vitals = listOf(
        HealthVital("bp", "Blood Pressure", "118/76", "mmHg", "Normal", VitalStatusType.NORMAL),
        HealthVital("hr", "Resting HR", "68", "bpm", "Steady", VitalStatusType.STEADY),
        HealthVital("glucose", "Glucose", "94", "mg/dL", "Fasting", VitalStatusType.FASTING)
    )

    val upcomingSchedule = listOf(
        ScheduleItem(
            id = "dental",
            title = "Dr. Marcus Chen",
            subtitle = "Routine Dental Checkup & Clean",
            dateText = "Nov 2 • 2:00 PM",
            iconType = ScheduleIconType.DENTAL
        ),
        ScheduleItem(
            id = "metabolic",
            title = "Comprehensive Metabolic Panel",
            subtitle = "Apex Diagnostic Center • Lab B",
            dateText = "Nov 12 • 8:30 AM (Fasting)",
            iconType = ScheduleIconType.LAB
        )
    )

    val careCircle = listOf(
        CareCircleMember("thorne", "Dr. Aris Thorne", "Primary Care", DR_THORNE_AVATAR, "(555) 234-8901"),
        CareCircleMember("lin", "Nurse Maya Lin", "Care Coordinator", NURSE_MAYA_AVATAR, "(555) 234-8902")
    )

    val doctors = listOf(
        Doctor(
            id = "vance",
            name = "Dr. Eleanor Vance, MD",
            title = "Chief of Cardiology",
            specialty = "Cardiology",
            hospital = "Cedars-Sinai Medical Center",
            rating = 4.9,
            reviewsCount = 184,
            experienceYears = 12,
            copayText = "$35 Copay (In-Net)",
            nextSlotText = "Today, 2:15 PM",
            hasInPerson = true,
            hasVideoConsult = true,
            photoUrl = DR_VANCE_AVATAR,
            isVerified = true,
            badge = "Chief of Cardiology",
            availableSlots = listOf("TODAY 2:15 PM", "TODAY 4:00 PM", "TOMORROW 9:30 AM")
        ),
        Doctor(
            id = "thorne",
            name = "Dr. Aris Thorne, MD",
            title = "Dermatologist & Clinical Researcher",
            specialty = "Dermatology",
            hospital = "Bay Skin Institute",
            rating = 4.8,
            reviewsCount = 96,
            experienceYears = 8,
            copayText = "$35 Copay (In-Net)",
            nextSlotText = "Tomorrow, 11:00 AM",
            hasInPerson = true,
            hasVideoConsult = false,
            photoUrl = DR_THORNE_AVATAR,
            isVerified = true,
            availableSlots = listOf("THU, OCT 24 11:00 AM", "THU, OCT 24 2:30 PM")
        ),
        Doctor(
            id = "patel",
            name = "Dr. Maya Patel, DO",
            title = "Family Medicine & Preventive Care",
            specialty = "Family Medicine",
            hospital = "Cedars-Sinai Medical Center",
            rating = 5.0,
            reviewsCount = 312,
            experienceYears = 10,
            copayText = "$20 Copay (In-Net)",
            nextSlotText = "Fri, Oct 25, 10:00 AM",
            hasInPerson = true,
            hasVideoConsult = true,
            isTelehealthReady = true,
            photoUrl = DR_PATEL_AVATAR,
            isVerified = true,
            isTopChoice = true,
            availableSlots = listOf("FRI, OCT 25 10:00 AM", "FRI, OCT 25 1:15 PM")
        )
    )

    val careRecords = listOf(
        CareRecord(
            id = "rec1",
            provider = "Dr. Eleanor Vance",
            clinicOrType = "Cardiology • St. Jude Heart Clinic",
            dateText = "Oct 10, 2024",
            durationOrTag = "45 min consult",
            title = "Annual Cardiovascular Evaluation & EKG",
            description = "Comprehensive telemetry, resting 12-lead EKG, and blood pressure profile update.",
            outcomeLabel = "CLINICAL OUTCOME",
            outcomeText = "Healthy sinus rhythm confirmed; Lisinopril dosage adjusted from 10mg to 5mg daily.",
            hasAudioNote = true,
            audioDurationSeconds = 105,
            pdfName = "Summary (PDF)",
            prescriptionsCount = 2,
            avatarUrl = DR_VANCE_AVATAR
        ),
        CareRecord(
            id = "rec2",
            provider = "Dr. Aris Thorne",
            clinicOrType = "Dermatology • Bay Skin Institute",
            dateText = "Aug 15, 2024",
            durationOrTag = "In-Clinic",
            title = "Routine Full-Body Skin Cancer Screening",
            description = "Dermoscopic examination of existing epidermal moles and sun-exposed regions.",
            outcomeLabel = "CLINICAL FINDING",
            outcomeText = "Benign dermoscopy evaluation across all 14 inspected lesions. No biopsy or excision recommended.",
            pdfName = "View Summary",
            avatarUrl = DR_THORNE_AVATAR
        ),
        CareRecord(
            id = "rec3",
            provider = "Quest Diagnostics",
            clinicOrType = "Central Pathology Laboratory #882",
            dateText = "May 3, 2024",
            durationOrTag = "Lab Work",
            title = "Comprehensive Blood & Lipid Panel",
            description = "Fasting 12-hour draw: CBC, metabolic set, lipid fractionations, & 25-OH Vitamin D.",
            outcomeLabel = "STATUS & FLAGS",
            outcomeText = "All standard parameters normal. 25-OH Vitamin D: 21 ng/mL (Low normal: 30-100 ng/mL). Supplementation suggested.",
            isFlagged = true,
            flaggedText = "1 Flagged Marker",
            isLab = true,
            pdfName = "Download Lab Report PDF (1.4 MB)"
        )
    )

    val emergencyContacts = listOf(
        EmergencyContact("ec1", "David Jenkins", "(Spouse)", "(555) 234-5678", isSpouse = true),
        EmergencyContact("ec2", "Dr. Robert Miller", "Primary Physician", "(555) 876-5432", isSpouse = false)
    )
}
