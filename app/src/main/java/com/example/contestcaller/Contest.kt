package com.example.contestcaller

import java.time.LocalDateTime
import java.time.LocalTime


data class Contest(
    val id: Int,
    val name: String,
    val type: String,
    val phase: String,
    val frozen: Boolean,
    val durationSeconds: Int,
    val freezeDurationSeconds: Int?,
    val startTimeSeconds: Int?,
    val relativeTimeSeconds: Int?,
    val preparedBy:	String?,
    val websiteUrl:	String?,
    val description: String?,
    val difficulty:	Integer?,
    val kind: String?,
    val icpcRegion: String?,
    val country: String?,
    val city: String?,
    val season: String?
)

data class StoredContestData(
    val id: Int,
    val name: String,
    val type: String,
    val phase: String,
    val frozen: Boolean,
    val durationSeconds: Int,
    val freezeDurationSeconds: Int?,
    val startTimeSeconds: LocalDateTime,
    val relativeTimeSeconds: Int?,
    val difficulty:	Integer?,
    val alarmSetStatus: Boolean,
    val alarm: List<LocalTime>
)

data class ContestResponse(
    val status: String,
    val result: List<Contest>
)
