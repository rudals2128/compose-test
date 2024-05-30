package kkm.test.compose.data.repository.remote

import kkm.test.compose.data.dto.KakaoPhotos

interface MainRepository {

    suspend fun getKakaoPhotos(query: String,page :Int, siez : Int): KakaoPhotos

}