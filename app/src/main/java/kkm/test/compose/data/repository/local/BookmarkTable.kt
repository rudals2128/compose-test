package kkm.test.compose.data.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_bookmark")
data class BookmarkTable (
    @PrimaryKey(autoGenerate = true)val id : Int,
    val collection : String,
    val thumbnail_url : String,
    val image_url : String,
    val witdh : Int,
    val height : Int,
    val display_sitename : String,
    val doc_url : String,
    val datetime : String,
    val searchTitle :String
)