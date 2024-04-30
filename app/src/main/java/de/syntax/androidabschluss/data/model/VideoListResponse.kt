package de.syntax.androidabschluss.data.model

data class VideoListResponse (
    val items: List<Item>
)

data class Item(
    val id: Id,
    val snippet: Snippet,
)

data class Id(
    val kind: String,
    val videoId: String?,
    val channelId: String?,
)

data class Snippet(
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
)

data class Thumbnails(
    val default: Default,
    val medium: Medium,
    val high: High,
)

data class Default(
    val url: String,
    val width: Long?,
    val height: Long?,
)

data class Medium(
    val url: String,
    val width: Long?,
    val height: Long?,
)

data class High(
    val url: String,
    val width: Long?,
    val height: Long?,
)
