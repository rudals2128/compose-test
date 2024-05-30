package kkm.test.compose.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kkm.test.compose.data.repository.local.BookmarkDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): BookmarkDatabase = Room
        .databaseBuilder(context, BookmarkDatabase::class.java, "database-bookmark")
        .build()

    @Singleton
    @Provides
    fun provideBookmarkDao(appDatabase: BookmarkDatabase) = appDatabase.BookmarkDao()
}