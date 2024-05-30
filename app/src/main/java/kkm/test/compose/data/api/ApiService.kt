package kkm.test.compose.data.api

import kkm.test.compose.data.dto.KakaoPhotos
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: KakaoAK 7be72aa7b2626b19f68fac2ac140d7d0")
    @GET("search/image")
    suspend fun getPhotos(
        @Query("query") title: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): KakaoPhotos

}