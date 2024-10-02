package com.amd.amdtest.activity

import android.os.Bundle

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amd.amdtest.adapter.NoteAdapter
import com.amd.amdtest.database.NoteDatabase
import com.amd.amdtest.databinding.ActivityHomeBinding
import com.amd.amdtest.repository.HomeRepository
import com.amd.amdtest.viewModel.HomeViewModel
import com.amd.amdtest.viewModel.HomeViewModelProviderFactory

class HomeActivity : BaseActivity() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: NoteAdapter
    private lateinit var manager: LinearLayoutManager
    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initViews()
    }

    private fun init(){
        val noteDatabase = NoteDatabase.getDatabase(this)
        val repository = HomeRepository.getInstance(noteDatabase.noteDao())
        val viewModelProviderFactory = HomeViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[HomeViewModel::class.java]
    }

    private fun initViews(){
        setupRV()
    }

    private fun setupRV(){
        adapter = NoteAdapter()
        manager = LinearLayoutManager(this)
        binding.rvNotes.layoutManager = manager
        binding.rvNotes.adapter = adapter
    }

        // Set up RecyclerView
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        noteAdapter = NoteAdapter() // Your RecyclerView Adapter
//        recyclerView.adapter = noteAdapter
//
//        // Observe LiveData from ViewModel
//        homeViewModel.allNote.observe(this, Observer { notes ->
//            // Update RecyclerView with the list of notes
//            notes?.let { noteAdapter.submitList(it) } // Assume your Adapter has a submitList method
//        })
//
//        // Set up UI components
//        val titleEditText = findViewById<EditText>(R.id.titleEditText)
//        val descEditText = findViewById<EditText>(R.id.descEditText)
//        val insertButton = findViewById<Button>(R.id.insertButton)
//
//        // Handle button click to insert a new note
//        insertButton.setOnClickListener {
//            val title = titleEditText.text.toString()
//            val desc = descEditText.text.toString()
//            if (title.isNotEmpty() && desc.isNotEmpty()) {
//                val note = Note(
//                    id = 0, // ID will be auto-generated
//                    title = title,
//                    desc = desc
//                )
//                homeViewModel.insert(note)
//                titleEditText.text.clear()
//                descEditText.text.clear()
//            }
//        }

        // Optionally, set up other buttons or interactions for update and delete operations

}