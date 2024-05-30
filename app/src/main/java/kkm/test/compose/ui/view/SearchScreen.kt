package kkm.test.compose.ui.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kkm.test.compose.data.dto.KakaoPhotos
import kkm.test.compose.data.repository.local.BookmarkTable
import kkm.test.compose.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SearchScreen() {

    val viewModel: MainViewModel = hiltViewModel()
    val photos = viewModel.photos.collectAsStateWithLifecycle()
    val bookmarks = viewModel.bookmark.collectAsStateWithLifecycle()
    var searchText by remember { mutableStateOf("") }

    Column {
        SearchBar(modifier = Modifier.fillMaxWidth(), onSearchTextChanged = { text ->
            searchText = text
        }, onTrailingIconClicked = {text ->
            searchText = text
        })
        MyRecyclerView(photos.value,bookmarks.value, viewModel, onBookmarkChange = {
            viewModel.insertBookmark(BookmarkTable(0,it.collection,it.thumbnail_url,it.image_url,it.witdh,it.height,it.display_sitename,it.doc_url,it.datetime,searchText))
        })
    }
    viewModel.getAllBookmark()
    viewModel.getKakaoPhotos(searchText, 1, 40)
    viewModel.getAllBookmark()



    Log.d("kkm", "북마크 = ${bookmarks.value}")




}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchTextChanged: (String) -> Unit,
    onTrailingIconClicked: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var job by remember { mutableStateOf<Job?>(null) }

//    DisposableEffect(searchText) {
//        onDispose {
//            job?.cancel()
//        }
//    }

    TextField(
        value = searchText,
        onValueChange = {
            searchText = it
            job?.cancel()
            job = CoroutineScope(Dispatchers.Default).launch {
                if(searchText.isNotEmpty()) {
                    delay(1000)
                    onSearchTextChanged(searchText)
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text(text = "검색어를 입력해주세요") },
        trailingIcon = {
            if(searchText.isNotEmpty()) {
                IconButton(onClick = { onTrailingIconClicked(searchText)}) {}

            }
            Icon(imageVector = Icons.Filled.Search, contentDescription = "search_icon") }

    )
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MyRecyclerView(
    photos: List<KakaoPhotos.Photos>,
    bookmarks: List<BookmarkTable>,
    viewModel: MainViewModel,
    onBookmarkChange:  (KakaoPhotos.Photos) -> Unit
) {
    if (photos.isNotEmpty()) {
        var isBookmark by remember { mutableStateOf<MutableList<Boolean>>(arrayListOf()) }

        LazyVerticalGrid(columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(4.dp)
        ){
            isBookmark.clear()

            items(photos) {item->
                Box(Modifier.clickable {
                    onBookmarkChange(item)
                } ) {
                    isBookmark.add(false)
                    GlideImage(
                        model = item.thumbnail_url,
                        contentDescription = item.display_sitename,
                        Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )

                    if (isBookmark.get(photos.indexOf(item))) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Bookmarked",
                            tint = Color.Red,
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable { isBookmark.add(photos.indexOf(item),false) }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Not Bookmarked",
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable { isBookmark.add(photos.indexOf(item),true) }
                        )
                    }
//                    Icon(
//                        imageVector =
//                        if(isBookmark){
//                            Icons.Default.Favorite
//                        } else {
//                            Icons.Default.FavoriteBorder
//                        },
//                        contentDescription = "Bookmarked",
//                        tint = Color.Red,
//                        modifier = Modifier
//                            .padding(4.dp).clickable {
//
//                            }
//
//                    )
                }
            }
        }
    }
}