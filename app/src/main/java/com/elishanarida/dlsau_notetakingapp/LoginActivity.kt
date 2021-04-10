package com.elishanarida.dlsau_notetakingapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth //Firebase Authentication variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance() // getInstance
        setupUI()
    }

    private fun setupUI() {
        val emailEditText = findViewById<EditText>(R.id.editTxt_email)
        val passwordEditText = findViewById<EditText>(R.id.editTxt_password)
        val loginButton = findViewById<Button>(R.id.btn_login)

        // if login button tapped
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validateFields(email, password)) {
                //if email and password is empty this will run
                Toast.makeText(this@LoginActivity, "Please input email or password", Toast.LENGTH_LONG).show()
            } else {
                login(email, password)
            }
        }
    }

    private fun validateFields(email: String, password: String) : Boolean {
        // if email or password is empty will return true
        return TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    intentToMain()
                } else {
                    Toast.makeText(this@LoginActivity, "Authentication failed.",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            // add automatic intent going to main activity since user is logged in
            intentToMain()
        }
    }

    private fun intentToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }
}