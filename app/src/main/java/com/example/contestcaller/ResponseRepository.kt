package com.example.contestcaller
//Don't know what it does and how it works yet
class ResponseRepository {
    suspend fun fetchContests(): List<Contest> {
        return RetrofitInstance.api.getContests().result
    }
}