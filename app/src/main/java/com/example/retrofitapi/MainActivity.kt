package com.example.retrofitapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.retrofitapi.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val logging = HttpLoggingInterceptor()
    private val authorizationInterceptor = AuthorizationInterceptor()
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(authorizationInterceptor)
        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://dog.ceo")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiDogService = retrofit.create(DogApiInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logging.level = HttpLoggingInterceptor.Level.BODY

        getDetails()

    }
    private fun setDetails(dataResult: Data){
        binding.tvDog.text = getString(R.string.String1, dataResult.message)
    }
    private fun getDetails(){
        lifecycleScope.launch {
            try {
                val details = apiDogService.getData()
                setDetails(details)
            }catch (e:java.lang.Exception){
                Log.d("MainActivity","ERROR ${e.message}")
            }
        }
    }
}