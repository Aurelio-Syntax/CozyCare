package de.syntax.androidabschluss.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.syntax.androidabschluss.data.model.DiaryEntries
import de.syntax.androidabschluss.data.model.Events
import de.syntax.androidabschluss.data.model.Tasks

@Dao
interface CozyCareDao {

    // Events für Calendar

    @Insert
    suspend fun insertEvent(event: Events): Long

    @Update
    suspend fun updateEvent(event: Events)

    @Query("DELETE FROM events_table WHERE id = :id")
    suspend fun deleteEventById(id: Int)

    @Query("SELECT * FROM events_table")
    fun getAllEvents(): LiveData<List<Events>>


    // Tasks für TodoFragment

    @Insert
    suspend fun insertTask(tasks: Tasks): Long

    @Update
    suspend fun updateTask(tasks: Tasks)

    @Query("SELECT * FROM tasks_table WHERE id = :taskId")
    fun getTaskById(taskId: Int): LiveData<Tasks>

    @Query("DELETE FROM tasks_table WHERE id = :id")
    suspend fun deleteTaskById(id: Int)

    @Query("SELECT * FROM tasks_table")
    fun getAllTasks(): LiveData<List<Tasks>>


    // DiaryEntry für Dankbarkeitstagebuch

    @Insert
    suspend fun insertDiaryEntry(entry: DiaryEntries): Long

    @Update
    suspend fun updateDiaryEntry(entry: DiaryEntries)

    @Query("DELETE FROM diary_table WHERE id = :id")
    suspend fun deleteEntryById(id: Int)

    @Query("SELECT * FROM diary_table")
    fun getAllEntries(): LiveData<List<DiaryEntries>>




}