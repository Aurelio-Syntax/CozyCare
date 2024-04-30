package de.syntax.androidabschluss.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events_table")
data class Events(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    var date: String,
    val time: String,
    val description: String
)