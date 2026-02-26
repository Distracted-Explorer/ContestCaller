package com.example.contestcaller

import java.time.LocalTime


data class Contest(
    val id: Int,
    val name: String,
    val type: String,
    val phase: String,
    val frozen: Boolean,
    val durationSeconds: Long,
    val freezeDurationSeconds: Int?,
    val startTimeSeconds: Long,
    val relativeTimeSeconds: Int?,
    val preparedBy:	String?,
    val websiteUrl:	String?,
    val description: String?,
    val difficulty:	Int?,
    val kind: String?,
    val icpcRegion: String?,
    val country: String?,
    val city: String?,
    val season: String?
)

//data to be stored in room database
data class StoredContestData(
    val id: Int,
    val name: String,
    val type: String,
    val phase: String,
    val frozen: Boolean,
    val durationSeconds: Long,
    val startTimeSeconds: Long,
    val startTime: String,
    val duration: String,
    val alarmSetStatus: Boolean,
    val alarm: List<LocalTime>
)

//API data revied datastructure
data class ContestResponse(
    val status: String,
    val result: List<Contest>
)

//temp data for preview to check
val tempStoredData: StoredContestData= StoredContestData(
    id = 11,
    name= "NEW Contest",
    type= "hard",
    phase= "before",
    frozen= true,
    durationSeconds = 1000,
    startTimeSeconds = 1000,
    startTime="05/05/2004",
    duration = "2:2:2",
    alarmSetStatus= false,  //todo need to update or set alarms
    alarm= listOf()  //todo 9need to work on default value for this

)

val tempList: List<StoredContestData> = listOf(tempStoredData,tempStoredData,tempStoredData,tempStoredData)