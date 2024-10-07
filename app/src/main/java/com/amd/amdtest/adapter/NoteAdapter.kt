package com.amd.amdtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amd.amdtest.activity.HomeActivity
import com.amd.amdtest.databinding.ItemRowTaskBinding
import com.amd.amdtest.model.Note
//
//class NoteAdapter():RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        TODO("Not yet implemented")
//    }
//
//    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//
//    inner class ItemViewHolder(val binding: ItemRowTaskBinding):RecyclerView.ViewHolder(binding.root){}
//
//
//    interface NoteItemClickListener {
//        fun onItemClick(item: Note)
//
//    }
//}

class NoteAdapter(
    private val onSwipeLeft: (Note) -> Unit,
    private val onSwipeRight: (Note) -> Unit
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemRowTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(private val binding: ItemRowTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.note = note
            binding.executePendingBindings()
        }
    }

    fun swipeLeft(position: Int) {
        val note = getItem(position)
        onSwipeLeft(note)
    }

    fun swipeRight(position: Int) {
        val note = getItem(position)
        onSwipeRight(note)
    }
}