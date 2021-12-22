package com.rawanalduhyshi.bookexchange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button = findViewById<Button>(R.id.button)

//        var button2 = findViewById<Button>(R.id.save)
//
//        button2.setOnClickListener {
//            save()
//        }
        val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract())
        { res ->
            this.onSignInResult(res)
        }

        val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        if (FirebaseAuth.getInstance().currentUser == null) {
            button.setOnClickListener {
                signInLauncher.launch(signInIntent)
                button.text = "SignIn"


            }
        } else {
            button.text = "SignOut"
            button.setOnClickListener { AuthUI.getInstance().signOut(this) }
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser

        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
        }


        fun save(){
            val db = Firebase.firestore
            val user = hashMapOf(
                "first" to "Email",
                "last" to "Password"
            )

// Add a new document with a generated ID
            db.collection("login")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }
    }
