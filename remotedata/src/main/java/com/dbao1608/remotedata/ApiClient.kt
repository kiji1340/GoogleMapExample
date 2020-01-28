package com.dbao1608.remotedata

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {

   companion object{
       private const val BASE_URL = "https://maps.googleapis.com/maps/api/"
       private var retrofit: Retrofit? = null
       var key: String = ""

       fun <S> createService(serviceClass: Class<S>): S {
           if(retrofit == null){
               synchronized(this){
                   val client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
                       var request= chain.request()

                       val url: HttpUrl =
                           request.url().newBuilder()
                               .addQueryParameter(
                                   "key"
                                   , key
                               )
                               .build()
                       request = request.newBuilder().url(url).build()
                       chain.proceed(request)
                   }).build()


                   retrofit = Retrofit.Builder()
                       .baseUrl(BASE_URL)
                       .client(client)
                       .addConverterFactory(GsonConverterFactory.create())
                       .build()
               }
           }

           return retrofit!!.create(serviceClass)
       }
   }
}