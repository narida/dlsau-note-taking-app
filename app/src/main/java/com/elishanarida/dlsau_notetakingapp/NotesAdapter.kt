package com.elishanarida.dlsau_notetakingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(val items: ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>() { //Adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.note.text = items[position]
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val notesTextView = view.findViewById<TextView>(R.id.txtViewNote)
    val note = notesTextView

}