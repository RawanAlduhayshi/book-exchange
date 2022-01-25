package com.rawanalduhyshi.bookexchange


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore
    lateinit var signInLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.btn)
        signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
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
            }
        } else {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        val button = findViewById<Button>(R.id.btn)

        if (FirebaseAuth.getInstance().currentUser != null) {
            button.text = "SignOut"
        } else {
            button.text = "SignIn"
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {

            val email = FirebaseAuth.getInstance().currentUser?.email
            val fullName = FirebaseAuth.getInstance().currentUser?.displayName
            val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            val documentReference: DocumentReference = db.collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            val user = hashMapOf(
                "fName" to fullName,
                "email" to email,
                "userId" to userId
            )
            db.collection("users").add(user)
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Error login", Toast.LENGTH_SHORT).show()
        }
    }
}