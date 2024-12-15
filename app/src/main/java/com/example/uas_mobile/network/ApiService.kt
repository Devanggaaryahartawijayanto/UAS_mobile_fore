package com.example.uas_mobile.network

import com.example.uas_mobile.model.Coffe
import com.example.uas_mobile.model.Users
import com.example.uas_mobile.request.CoffeRequest
import com.example.uas_mobile.response.CoffeResponse
import com.example.uas_mobile.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("coffes")
    fun getAllCoffe(): Call<List<Coffe>>
    //
    @POST("coffes")
    fun createCoffe(@Body request: CoffeRequest): Call<CoffeResponse>
    //
    @POST("coffes/{id}")
    fun updateCoffe(@Path("id") id: String, @Body request: CoffeRequest): Call<CoffeResponse>
    //
    @DELETE("coffes/{id}")
    fun deleteCoffe(@Path("id") id: String): Call<CoffeResponse>

    @POST("account")
    fun createUser(@Body request: Users): Call<RegisterResponse>

    @GET("account")
    fun getAllUsers(): Call<List<Users>>
}
