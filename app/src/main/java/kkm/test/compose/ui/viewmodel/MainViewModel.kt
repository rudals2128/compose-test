package kkm.test.compose.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kkm.test.compose.data.dto.KakaoPhotos
import kkm.test.compose.data.repository.local.BookmarkRepository
import kkm.test.compose.data.repository.local.BookmarkTable
import kkm.test.compose.data.repository.remote.MainRepository
import kkm.test.compose.ui.util.NetworkHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val application: Application,
    private val mainRepo: MainRepository,
    private val networkHelper: NetworkHelper,
    private val bookmarkRepo: BookmarkRepository,
) : AndroidViewModel(application) {
    private val _photos = MutableStateFlow<List<KakaoPhotos.Photos>>(emptyList())
    val photos = _photos

    private val _kakaoResult = MutableStateFlow<KakaoPhotos.KakaoResult?>(null)
    val kakaoResult = _kakaoResult

    private val _bookmark = MutableStateFlow<List<BookmarkTable>>(emptyList())
    val bookmark = _bookmark

    private val _checkBookmark = MutableStateFlow<Boolean>(false)
    val checkBookmark = _checkBookmark

    fun getKakaoPhotos(query : String, page : Int, size : Int) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                kotlin.runCatching {
                    val data = mainRepo.getKakaoPhotos(query, page, size)
                    _photos.value = data.documents
                    _kakaoResult.value = data.meta

                }.onFailure{
                    android.util.Log.d("kkm", " 에러 ${it.printStackTrace()} // ${it.message}")
                }
            }
        }
    }

    fun getBookmark(searchText : String){
        viewModelScope.launch {
            kotlin.runCatching {
                _bookmark.value = bookmarkRepo.getBookmark(searchText)
            }
        }
    }

    fun getAllBookmark(){
        viewModelScope.launch {
            kotlin.runCatching {
                _bookmark.value = bookmarkRepo.getAllBookmark()
            }
        }
    }

    fun insertBookmark(item: BookmarkTable){
        viewModelScope.launch {
            kotlin.runCatching {
                bookmarkRepo.insertBookmark(item)
            }
        }
    }

    fun deleteBookmark(items : String){
        viewModelScope.launch {
            kotlin.runCatching {
                bookmarkRepo.deleteBookmark(items)
            }
        }
    }

    fun checkBookmark(url : String){
        viewModelScope.launch {
            kotlin.runCatching {
                bookmarkRepo.checkBookmark(url)
            }
        }
    }
}