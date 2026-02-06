package com.example.contestcaller

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//API calling
interface APIIinterface {
    @GET("contest.list")
    suspend fun getContests(): ContestResponse
}


//Using retrofit to call api over internet
object RetrofitInstance {
    private const val BASE_URL = "https://codeforces.com/api/"

    val api: APIIinterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIIinterface::class.java)
    }
}
