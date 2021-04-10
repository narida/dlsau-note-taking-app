package com.elishanarida.dlsau_notetakingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

//    notes [
//    "zxcsd": "hows your day",
//    "weqeasdz": "asdsay",
//    "asdqwexzc": "asdasdsa",
//    "qweqwdaz": "note4"
//    ]

    private lateinit var recyclerView: RecyclerView
    private var notes: ArrayList<String> = ArrayList()
    private val database = Firebase.database //Initialize firebase Database
    private val databaseReference = database.reference.child("notes") //Creating child (folder) on database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        loadNotes()
    }

    private fun setupUI() {
        recyclerView = findViewById(R.id.recylcerView)
        recyclerView.layoutManager = LinearLayoutManager(this) // Creates a vertical Layout Manager
        recyclerView.adapter = NotesAdapter(notes, this)

        val addNotesButton = findViewById<Button>(R.id.btn_add)
        addNotesButton.setOnClickListener {
            intentToAddons()
        }
    }

    private fun loadNotes() { //Gets all notes from firebase database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) { //if data changes from firebase
                //snapshot = data
                // snapsho.children = "notes"
                notes.clear()
                for (data in snapshot.children) { //get all objects inside notes folder
                    val value = data.getValue<String>().toString()
                    notes.add(value) //adds value from firebase to notes array
                    Log.d("DATAEJ", value)
                }
                recyclerView.adapter?.notifyDataSetChanged() //update recyclerview
            }

            override fun onCancelled(error: DatabaseError) { //if data fails to read
                Toast.makeText(this@MainActivity, "Error loading data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun intentToAddons() {
        val intent = Intent(this@MainActivity, AddNotesActivity::class.java)
        startActivity(intent)
    }
}