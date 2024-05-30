package kkm.test.compose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kkm.test.compose.data.repository.local.BookmarkTable

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM tb_bookmark")
    suspend fun getAll(): List<BookmarkTable>

    @Query("DELETE FROM tb_bookmark Where thumbnail_url IN (:items)")
    suspend fun delete(items : String)

    @Insert
    suspend fun insert(vararg item: BookmarkTable)

    @Query("SELECT * FROM tb_bookmark WHERE searchTitle LIKE :searchText")
    suspend fun searchData(searchText : String) : List<BookmarkTable>

    @Query("SELECT COUNT(*) FROM tb_bookmark WHERE thumbnail_url = :url")
    suspend fun checkBookmark(url: String): Boolean
}