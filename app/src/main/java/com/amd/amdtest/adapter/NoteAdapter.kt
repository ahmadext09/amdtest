package com.amd.amdtest.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amd.amdtest.activity.HomeActivity
import com.amd.amdtest.databinding.ItemRowTaskBinding
import com.amd.amdtest.model.Note

class NoteAdapter():RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class ItemViewHolder(val binding: ItemRowTaskBinding):RecyclerView.ViewHolder(binding.root){}


    interface NoteItemClickListener {
        fun onItemClick(item: Note)

    }
}