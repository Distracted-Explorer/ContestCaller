package com.example.contestcaller

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
    val durationSeconds: Int,
    val freezeDurationSeconds: Int?,
    val startTimeSeconds: Int?,  //todo need to convert to date and time
    val relativeTimeSeconds: Int?,
    val difficulty:	Int?,
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
    durationSeconds= 18000,
    freezeDurationSeconds= 5000,  //todo need to convert to date and time
    startTimeSeconds=25000, //todo need to convert to date and time
    relativeTimeSeconds=6000,   //todo need to convert to date and time
    difficulty= 5,
    alarmSetStatus= false,  //todo need to update or set alarms
    alarm= listOf()  //todo 9need to work on default value for this

)

val tempList: List<StoredContestData> = listOf(tempStoredData,tempStoredData,tempStoredData,tempStoredData)