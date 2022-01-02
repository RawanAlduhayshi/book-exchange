package com.rawanalduhyshi.bookexchange

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button = findViewById<Button>(R.id.btn)
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

//                var intent = Intent(this,HomeActivity::class.java)
//                startActivity(intent)
            }
        } else {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            button.text = "SignOut"
            button.setOnClickListener { AuthUI.getInstance().signOut(this) }
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }
}







