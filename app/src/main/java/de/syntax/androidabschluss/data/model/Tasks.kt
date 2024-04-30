package de.syntax.androidabschluss.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_table")
data class Tasks(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var text: String,
    var checked: Boolean

)