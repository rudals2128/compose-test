package kkm.test.compose.domain.repository

import kkm.test.compose.data.api.ApiService
import kkm.test.compose.data.di.ApplicationModule
import kkm.test.compose.data.dto.KakaoPhotos
import kkm.test.compose.data.repository.remote.MainRepository
import javax.inject.Inject

class MainRepositoryImpl@Inject constructor(
    @ApplicationModule.BaseRetrofit private val apiService: ApiService
): MainRepository {

    override suspend fun getKakaoPhotos(query: String,page :Int, size : Int): KakaoPhotos = apiService.getPhotos(query,page, size)

}