package com.amd.amdtest.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.amd.amdtest.model.Note
import com.amd.amdtest.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    val app: Application, private val repository: HomeRepository
) : AndroidViewModel(app) {

    val allNote: LiveData<List<Note>> = repository.allNotes


    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }


    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }


    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

}