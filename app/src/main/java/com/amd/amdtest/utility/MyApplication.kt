package com.amd.amdtest.utility

import android.app.Application
import com.amd.amdtest.database.NoteDatabase
import com.amd.amdtest.repository.HomeRepository

class MyApplication: Application() {

    val database by lazy { NoteDatabase.getDatabase(this) }
    val repository by lazy { HomeRepository.getInstance(database.noteDao()) }
}