package com.elishanarida.dlsau_notetakingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddNotesActivity : AppCompatActivity() {

    private val database = Firebase.database //Initialize firebase Database
    private val databaseReference = database.reference.child("notes") //Creating child (folder) on database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        setupUI()
    }

    private fun setupUI() {
        val notesEditText = findViewById<EditText>(R.id.txt_note)
        val addNotesButton = findViewById<Button>(R.id.btn_addNote)

        addNotesButton.setOnClickListener {
            val note = notesEditText.text.toString()

            if (TextUtils.isEmpty(note)) { //if note is empty
                Toast.makeText(this@AddNotesActivity, "Error please add note", Toast.LENGTH_LONG).show()
            } else {
                val id = databaseReference.push().key.toString() //Creates unique id from databaseRef
                databaseReference.child(id).setValue(note) //push data to firebase

                //Intent to main
                val intent = Intent(this@AddNotesActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}