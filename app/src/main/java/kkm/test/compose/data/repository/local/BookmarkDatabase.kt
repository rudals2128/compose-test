package kkm.test.compose.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import kkm.test.compose.data.dao.BookmarkDao

@Database(entities = [BookmarkTable::class], version = 1, exportSchema = false)
abstract class BookmarkDatabase :  RoomDatabase() {

    abstract fun BookmarkDao(): BookmarkDao
}