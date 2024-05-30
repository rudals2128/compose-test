package kkm.test.compose.data.dto

data class KakaoPhotos(
    val meta: KakaoResult,
    val documents: ArrayList<Photos>,
){
    data class KakaoResult(
        val is_end : Boolean,
        val pageable_count : Int,
        val total_count : Int
    )
    data class Photos(
        val collection : String,
        val thumbnail_url : String,
        val image_url : String,
        val witdh : Int,
        val height : Int,
        val display_sitename : String,
        val doc_url : String,
        val datetime : String
    )
}
