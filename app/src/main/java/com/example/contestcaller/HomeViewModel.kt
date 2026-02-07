package com.example.contestcaller

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.Int
import kotlin.String

//backend of data working in app
class HomeViewModel : ViewModel() {
    private val repository = ResponseRepository()
    private val _contests = MutableStateFlow<List<Contest>>(emptyList())

    // DERIVED STATE
    val contests: StateFlow<List<StoredContestData>> =
        _contests
            // is as for each giving output for every incomming data we find
            .map { contestList ->
                neededValue(contestList)
            }
            //need to study what it ment
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadContests()
    }

    private fun loadContests() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _contests.value = repository.fetchContests()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
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