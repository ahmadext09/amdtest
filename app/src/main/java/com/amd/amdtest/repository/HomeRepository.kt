package com.amd.amdtest.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.amd.amdtest.dao.NoteDao
import com.amd.amdtest.model.Note


class HomeRepository private constructor(private val noteDao: NoteDao) {


    val allNotes: LiveData<List<Note>> = noteDao.getALLNotes()

    @WorkerThread
    suspend fun insert(note: Note) {
        noteDao.insertNote(note)
    }


    @WorkerThread
    suspend fun delete(note: Note) {
        noteDao.deleteNote(note)
    }

    @WorkerThread
    suspend fun update(note: Note) {
        noteDao.updateNote(note)
    }

    companion object {
        @Volatile
        private var INSTANCE: HomeRepository? = null

        fun getInstance(noteDao: NoteDao): HomeRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = HomeRepository(noteDao)
                INSTANCE = instance
                instance
            }
        }
    }
}


