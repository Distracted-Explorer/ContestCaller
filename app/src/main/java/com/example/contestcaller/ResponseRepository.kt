package com.example.contestcaller

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//Don't know what it does and how it works yet
class ResponseRepository {
    suspend fun fetchContests(): List<StoredContestData> {
        val response = RetrofitInstance.api.getContests().result;
        return neededValue(response)
    }
}

fun neededValue( _contest: List<Contest> ):List<StoredContestData>{
    val updatedContestList : MutableList<StoredContestData> = mutableListOf<StoredContestData>()
    for(contestItem in _contest){
        when(contestItem.phase){
            "BEFORE" -> updatedContestList.add(
                StoredContestData(
                    id = contestItem.id,
                    name= contestItem.name,
                    type= contestItem.type,
                    phase= contestItem.phase,
                    frozen= contestItem.frozen,
                    startTime=convertUtcToUserTime(contestItem.startTimeSeconds),
                    durationSeconds= contestItem.durationSeconds,
                    startTimeSeconds=contestItem.startTimeSeconds,
                    alarmSetStatus= false,  //todo need to update or set alarms
                    alarm= listOf()  //todo need to work on default value for this
                )
            )
            "CODING" -> updatedContestList.add(
                StoredContestData(
                    id = contestItem.id,
                    name= contestItem.name,
                    type= contestItem.type,
                    phase= contestItem.phase,
                    frozen= contestItem.frozen,
                    startTime=convertUtcToUserTime(contestItem.startTimeSeconds),
                    durationSeconds= contestItem.durationSeconds,
                    startTimeSeconds=contestItem.startTimeSeconds,
                    alarmSetStatus= false,  //todo need to update or set alarms
                    alarm= listOf()  //todo need to work on default value for this
                )
            )
            //todo make it for pnding system test
//            "PENDING_SYSTEM_TEST" -> updatedContestList.add(
//                StoredContestData(
//                    id = contestItem.id,
//                    name= contestItem.name,
//                    type= contestItem.type,
//                    phase= contestItem.phase,
//                    frozen= contestItem.frozen,
//                    durationSeconds= contestItem.durationSeconds,
//                    freezeDurationSeconds= contestItem.freezeDurationSeconds,  //todo need to convert to date and time
//                    startTimeSeconds=convertUtcToUserTime(contestItem.startTimeSeconds?:0L), //todo need to convert to date and time
//                    TimePassedSeconds=contestItem.relativeTimeSeconds,   //todo need to convert to date and time
//                    alarmSetStatus= false,  //todo need to update or set alarms
//                    alarm= listOf()  //todo 9need to work on default value for this
//                )
//            )
        }
    }
    return updatedContestList
}

fun convertUtcToUserTime(epochSeconds: Long): String {

    // 1️⃣ Create Instant from UTC epoch
    val instant = Instant.ofEpochSecond(epochSeconds)

    // 2️⃣ Get user's timezone
    val userZone = ZoneId.systemDefault()

    // 3️⃣ Convert instant to user's zone
    val zonedDateTime = instant.atZone(userZone)

    // 4️⃣ Format nicely
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")

    return zonedDateTime.format(formatter)
}

