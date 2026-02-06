package com.example.contestcaller

class ResponseRepository {
    suspend fun fetchContests(): List<Contest> {
        return RetrofitInstance.api.getContests().result
    }
}