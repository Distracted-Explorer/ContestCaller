package com.example.contestcaller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.String

//backend of data working in app
class HomeViewModel : ViewModel() {
    private val repository = ResponseRepository()

    private val _contests = MutableStateFlow<List<StoredContestData>>(emptyList())
    val contests: StateFlow<List<StoredContestData>> =_contests

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


