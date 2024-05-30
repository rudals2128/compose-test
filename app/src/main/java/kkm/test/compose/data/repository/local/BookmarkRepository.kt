package kkm.test.compose.data.repository.local

interface BookmarkRepository {

    suspend fun getBookmark(searchText : String) : List<BookmarkTable>
    suspend fun getAllBookmark() : List<BookmarkTable>
    suspend fun deleteBookmark(items : String)
    suspend fun insertBookmark(item: BookmarkTable)
    suspend fun checkBookmark(url: String) : Boolean

}