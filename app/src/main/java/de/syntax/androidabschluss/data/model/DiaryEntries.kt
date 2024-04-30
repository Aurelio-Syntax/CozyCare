package de.syntax.androidabschluss.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary_table")
data class DiaryEntries (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var text: String
)