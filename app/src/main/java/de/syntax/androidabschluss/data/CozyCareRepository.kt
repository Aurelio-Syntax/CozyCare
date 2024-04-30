package de.syntax.androidabschluss.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.syntax.androidabschluss.data.local.CozyCareDatabase
import de.syntax.androidabschluss.data.model.DiaryEntries
import de.syntax.androidabschluss.data.model.Events
import de.syntax.androidabschluss.data.model.Tasks

class CozyCareRepository (private val database: CozyCareDatabase) {


    val eventsList: LiveData<List<Events>> = database.cozyCareDao.getAllEvents()
    val tasksList: LiveData<List<Tasks>> = database.cozyCareDao.getAllTasks()
    val diaryEntriesList: LiveData<List<DiaryEntries>> = database.cozyCareDao.getAllEntries()

    // Events für den Calendar


    suspend fun insertEvent(events: Events) {
        try {
            database.cozyCareDao.insertEvent(events)
        } catch (e: Exception) {
            Log.e("Repo", "Error insert Event in DB: $e")
        }
    }

    suspend fun deleteEvent(events: Events) {
        try {
            database.cozyCareDao.deleteEventById(events.id)
        } catch (e: Exception) {
            Log.e("Repo", "Error delete Event from DB: $e")
        }
    }

    // Tasks für ToDoFunktion ----------------------------------------


    suspend fun insertTask(tasks: Tasks) {
        try {
            database.cozyCareDao.insertTask(tasks)
        } catch (e: Exception) {
            Log.e("Repo", "Error insert Task in DB: $e")
        }
    }

    fun getTaskById(taskId: Int): LiveData<Tasks> {
        val taskList = MutableLiveData<Tasks>()
        try {
            database.cozyCareDao.getTaskById(taskId)
        } catch (e: Exception) {
            Log.e("Repo", "Error getting Task from DB: $e")
        }
        return taskList
    }

    suspend fun updateTask(tasks: Tasks) {
        try {
            database.cozyCareDao.updateTask(tasks)
        } catch (e: Exception) {
            Log.e("Repo", "Error update Task in DB: $e")
        }
    }

    suspend fun deleteTask(tasks: Tasks) {
        try {
            database.cozyCareDao.deleteTaskById(tasks.id)
        } catch (e: Exception) {
            Log.e("Repo", "Error delete Task from DB: $e")
        }
    }


    // Funktionen für Dankbarkeitstagebuch -------------------------------


    suspend fun insertDiaryEntry(entries: DiaryEntries) {
        try {
            database.cozyCareDao.insertDiaryEntry(entries)
        } catch (e: Exception) {
            Log.e("Repo", "Error insert Task in DB: $e")
        }
    }

    suspend fun updateDiaryEntry(entries: DiaryEntries) {
        try {
            database.cozyCareDao.updateDiaryEntry(entries)
        } catch (e: Exception) {
            Log.e("Repo", "Error update Task in DB: $e")
        }
    }

    suspend fun deleteDiaryEntry(entries: DiaryEntries) {
        try {
            database.cozyCareDao.deleteEntryById(entries.id)
        } catch (e: Exception) {
            Log.e("Repo", "Error delete Task from DB: $e")
        }
    }
}