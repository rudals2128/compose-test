package kkm.test.compose.data.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kkm.test.compose.data.api.ApiService
import kkm.test.compose.data.repository.local.BookmarkRepository
import kkm.test.compose.data.repository.remote.MainRepository
import kkm.test.compose.domain.repository.BookmarkRepositoryImpl
import kkm.test.compose.domain.repository.MainRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Retention(AnnotationRetention.BINARY)
    annotation class BaseRetrofit


    @Provides
    @BaseRetrofit
    fun provideBaseUrl() = "https://dapi.kakao.com/v2/"


    @Provides
    @Singleton
    @BaseRetrofit
    fun provideBaseOkHttpClient() = run {
        val loggingInterceptor = HttpLoggingInterceptor()
        val interceptor = CustomInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    @BaseRetrofit
    fun provideRetrofit(
        @BaseRetrofit okHttpClient: OkHttpClient,
        @BaseRetrofit BaseUrl: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BaseUrl)
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    @BaseRetrofit
    fun provideApiService(@BaseRetrofit baseRetrofit: Retrofit): ApiService = baseRetrofit.create(ApiService::class.java)



    @Provides
    @Singleton
    @BaseRetrofit
    fun provideMainRepo(repository: MainRepositoryImpl): MainRepository = repository

    @Provides
    @Singleton
    fun provideBookmarkRepo(repository: BookmarkRepositoryImpl): BookmarkRepository = repository



}


class CustomInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        // Request Header 추가
        val request = chain.request()
        request.newBuilder().addHeader("content-type", "charset=UTF-8").build()
        request.newBuilder().addHeader("what-you-want-header", "data").build()

        // Response 값 처리
        val response = chain.proceed(request)


        // 데이터의 정상 여부에 따라 결과를 변환하여 처리
        return if (response.code == 200) {
            Log.d("kkm", "response.m11111essage= ${response.message}, ${response.body}")
            val bodyString = response.body?.string()?.replace(")]}',", "")?.trim() ?: ""

            val body = ResponseBody.create(response.body?.contentType(),bodyString)
            response.newBuilder().body(body).build()
        } else {
            Log.d("kkm", "response.message= ${response.message}, ${response.body}")
            response
        }
    }

}