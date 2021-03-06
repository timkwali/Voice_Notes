package com.example.voicenotes.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.example.voicenotes.data.Note
import com.example.voicenotes.databinding.NotesItemBinding
import com.example.voicenotes.utils.OnItemClickListener

class NotesRVAdapter (private val notes: List<Note>, private val clickListener: OnItemClickListener<Note>):
        RecyclerView.Adapter<NotesRVAdapter.NotesViewHolder>() {

    inner class NotesViewHolder(private val binding: NotesItemBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(note: Note, action: OnItemClickListener<Note>) {
                binding.apply {
                    val date = note.date.split(" ")
                    noteItemNoteTv.text = note.note
                    noteItemDateTv.text = "${date[0]} ${date[1].substring(0, 3)} '${date[2].substring(2)}"
                    noteItemTimeTv.text = note.time
                }

                itemView.setOnClickListener {
                    action.onItemClick(note, adapterPosition)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.bind(currentNote, clickListener)
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}