package com.amd.amdtest.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amd.amdtest.adapter.NoteAdapter
import com.amd.amdtest.database.NoteDatabase
import com.amd.amdtest.databinding.ActivityHomeBinding
import com.amd.amdtest.model.Note
import com.amd.amdtest.repository.HomeRepository
import com.amd.amdtest.viewModel.HomeViewModel
import com.amd.amdtest.viewModel.HomeViewModelProviderFactory

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var editNoteLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        init()

        // Initialize Views
        initViews()
    }

    private fun init() {
        val noteDatabase = NoteDatabase.getDatabase(this)
        val repository = HomeRepository.getInstance(noteDatabase.noteDao())
        val viewModelProviderFactory = HomeViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[HomeViewModel::class.java]
    }

    private fun initViews() {
        setupRecyclerView()
        setupFAB()
        setupActivityResultLauncher()
        observeNotes()
    }

    private fun setupRecyclerView() {
        NoteAdapter(
            onSwipeLeft = { note -> openEditActivity(note) },
            onSwipeRight = { note -> viewModel.delete(note) }
        ).also { noteAdapter = it }

        binding.rvNotes.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> noteAdapter.swipeLeft(position)
                    ItemTouchHelper.RIGHT -> noteAdapter.swipeRight(position)
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvNotes)
    }

    private fun setupFAB() {
        binding.btnAdd.setOnClickListener {
            openAddNoteActivity()
        }
    }

    private fun openAddNoteActivity() {
        val intent = Intent(this, AddNoteActivity::class.java)
        editNoteLauncher.launch(intent) // Use the launcher for adding a note
    }

    private fun openEditActivity(note: Note? = null) {
        val intent = Intent(this, AddNoteActivity::class.java)
        if (note != null) {
            val bundle = Bundle()
            bundle.putSerializable("note", note)
            intent.putExtras(bundle)
            intent.putExtra("isEditing", true)
        } else {
            intent.putExtra("isEditing", false)
        }
        editNoteLauncher.launch(intent)
    }

    private fun setupActivityResultLauncher() {
        editNoteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val editedNote = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getSerializableExtra("note", Note::class.java)
                } else {
                    result.data?.getSerializableExtra("note") as? Note
                }

                val isEditing = result.data?.getBooleanExtra("isEditing", false) ?: false

                editedNote?.let {
                    if (isEditing) {
                        viewModel.update(it) // Update the existing note in the Room DB
                    } else {
                        viewModel.insert(it) // Insert a new note into the Room DB
                    }
                }
            }
        }
    }
    private fun observeNotes() {
        viewModel.allNote.observe(this) { notes ->
            noteAdapter.submitList(notes)
        }
    }
}

