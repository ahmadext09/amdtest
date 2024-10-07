package com.amd.amdtest.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.amd.amdtest.R

import com.amd.amdtest.databinding.ActivityAddNoteBinding
import com.amd.amdtest.model.Note

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private var isEditing: Boolean = false // Declare at class level
    private var note: Note? = null // Declare at class level

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)

        init()
        initViews()
    }

    private fun init() {}

    private fun initViews() {
        fetchIntentData()
        handleSaveClick()
    }

    private fun fetchIntentData() {
        isEditing = intent.getBooleanExtra("isEditing", false)
        if (isEditing) {
            // Fetch the Bundle first
            val bundle = intent.extras
            // Then get the Note from the Bundle
            note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle?.getSerializable("note", Note::class.java)
            } else {
                bundle?.getSerializable("note") as? Note
            }

            note?.let {
                binding.etTitle.setText(it.title)
                binding.etNote.setText(it.desc)
            }
        }
    }

    private fun handleSaveClick() {
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val noteContent = binding.etNote.text.toString()

            // Create a new note or update existing note
            val noteToReturn = Note(
                id = if (isEditing) note?.id ?: 0 else 0, // Use existing note ID if editing
                title = title,
                desc = noteContent
            )

            // Return the result with the note and isEditing flag
            val resultIntent = Intent().apply {
                putExtra("note", noteToReturn) // Send the note back
                putExtra("isEditing", isEditing) // Send the editing status back
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}