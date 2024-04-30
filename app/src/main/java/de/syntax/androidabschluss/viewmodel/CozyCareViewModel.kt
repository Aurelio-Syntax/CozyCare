package de.syntax.androidabschluss.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.syntax.androidabschluss.data.CozyCareRepository
import de.syntax.androidabschluss.data.local.getDatabase
import de.syntax.androidabschluss.data.model.DiaryEntries
import de.syntax.androidabschluss.data.model.Events
import de.syntax.androidabschluss.data.model.Tasks
import kotlinx.coroutines.launch


class CozyCareViewModel (application: Application): AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val cozyCareRepository = CozyCareRepository(database)

    val eventsList = cozyCareRepository.eventsList
    val tasksList = cozyCareRepository.tasksList
    val diaryEntriesList = cozyCareRepository.diaryEntriesList


    private val _events = MutableLiveData<List<Events>>()

    val events: LiveData<List<Events>>
        get() = _events


    private val _complete = MutableLiveData<Boolean>()

    val complete: LiveData<Boolean>
        get() = _complete

    private val _selectedTask = MutableLiveData<Tasks?>()

    val selectedTask: LiveData<Tasks?>
        get() = _selectedTask



    // Calendar Funktion

    fun insertEvent(event: Events) {
        viewModelScope.launch {
            cozyCareRepository.insertEvent(event)
            _complete.value = true
        }
    }

    fun deleteEvent(event: Events) {
        viewModelScope.launch {
            cozyCareRepository.deleteEvent(event)
            _complete.value = true
        }
    }


    // ToDoFunktion --------------------------------

    fun insertTask(task: Tasks) {
        viewModelScope.launch {
            cozyCareRepository.insertTask(task)
            _complete.value = true
        }
    }

    fun deleteTask(task: Tasks) {
        viewModelScope.launch {
            cozyCareRepository.deleteTask(task)
            _complete.value = true
        }
    }

    fun getTaskById(taskId: Int): LiveData<Tasks> {
        val taskList = MutableLiveData<Tasks>()
        viewModelScope.launch {
            cozyCareRepository.getTaskById(taskId)
        }
        return taskList
    }

    fun updateTask(task: Tasks) {
        viewModelScope.launch {
            cozyCareRepository.updateTask(task)
            _complete.value = true
        }
    }

    // Funktionen für DiaryBook -----------------------------

    fun insertDiaryEntry(entries: DiaryEntries) {
        viewModelScope.launch {
            cozyCareRepository.insertDiaryEntry(entries)
            _complete.value = true
        }
    }

    fun deleteDiaryEntry(entries: DiaryEntries) {
        viewModelScope.launch {
            cozyCareRepository.deleteDiaryEntry(entries)
            _complete.value = true
        }
    }

    fun updateDiaryEntry(entries: DiaryEntries) {
        viewModelScope.launch {
            cozyCareRepository.updateDiaryEntry(entries)
            _complete.value = true
        }
    }
}