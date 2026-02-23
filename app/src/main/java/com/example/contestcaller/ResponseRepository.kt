package com.example.contestcaller
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
                    durationSeconds= contestItem.durationSeconds,
                    freezeDurationSeconds= contestItem.freezeDurationSeconds,  //todo need to convert to date and time
                    startTimeSeconds=contestItem.startTimeSeconds, //todo need to convert to date and time
                    relativeTimeSeconds=contestItem.relativeTimeSeconds,   //todo need to convert to date and time
                    difficulty= contestItem.difficulty,
                    alarmSetStatus= false,  //todo need to update or set alarms
                    alarm= listOf()  //todo 9need to work on default value for this
                )
            )
            //todo for coding as well
        }
    }
    return updatedContestList
}