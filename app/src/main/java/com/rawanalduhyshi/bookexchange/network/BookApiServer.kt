package com.rawanalduhyshi.bookexchange.network

import com.rawanalduhyshi.bookexchange.DataResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL ="https://www.googleapis.com/books/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()



interface BookApiServer{
    @GET("v1/volumes?q=flowers+inauthor:keyes&key=AIzaSyDSc5_H_6Jb418SmCOhSD3a-3ODfOKlBSA")
    suspend fun getBooksInfo(): DataResponse
}

object MovieApi {
    val retrofitServer: BookApiServer by lazy {
        retrofit.create(BookApiServer::class.java)
    }
}