package com.dbao1608.googlemapexample.remotedata

import com.dbao1608.googlemapexample.Config
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {

   companion object{
       private const val BASE_URL = "https://maps.googleapis.com/maps/api/"
       private var retrofit: Retrofit? = null

       fun <S> createService(serviceClass: Class<S>): S {
           if(retrofit == null){
               synchronized(this){
                   val client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
                       var request= chain.request()

                       val url: HttpUrl =
                           request.url().newBuilder()
                               .addQueryParameter(
                                   "key"
                                   , Config.getInstance().getGoogleMapKey()
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