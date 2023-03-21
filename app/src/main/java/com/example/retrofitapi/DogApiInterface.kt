package com.example.retrofitapi

import retrofit2.http.GET

interface DogApiInterface {
        @GET("/api/breeds/list/all")
        suspend fun getData() : Data
}