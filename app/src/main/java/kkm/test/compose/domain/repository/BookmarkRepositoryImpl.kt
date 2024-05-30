package kkm.test.compose.domain.repository

import kkm.test.compose.data.dao.BookmarkDao
import kkm.test.compose.data.repository.local.BookmarkRepository
import kkm.test.compose.data.repository.local.BookmarkTable
import javax.inject.Inject

class BookmarkRepositoryImpl@Inject constructor(
    private val bookmarkDao: BookmarkDao
): BookmarkRepository {

    override suspend fun getBookmark(searchText: String): List<BookmarkTable> = bookmarkDao.searchData(searchText)
    override suspend fun getAllBookmark(): List<BookmarkTable> = bookmarkDao.getAll()

    override suspend fun deleteBookmark(items: String) = bookmarkDao.delete(items)


    override suspend fun insertBookmark(item: BookmarkTable) = bookmarkDao.insert(item)
    override suspend fun checkBookmark(url: String): Boolean = bookmarkDao.checkBookmark(url)


}