//package com.rawanalduhyshi.bookexchange
//import android.annotation.SuppressLint
//import android.app.Activity.RESULT_OK
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.firebase.ui.auth.AuthUI
//import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
//import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
//import com.google.firebase.auth.FirebaseAuth
//import com.rawanalduhyshi.bookexchange.databinding.FragmentLoginBinding
//
//
//class LoginFragment : Fragment() {
////    private var  binding:FragmentLoginBinding? = null
//
//
//     private val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract())
//     { res ->
//         this.onSignInResult(res)
//     }
//     private val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
//
//     // Create and launch sign-in intent
//     private val signInIntent = AuthUI.getInstance()
//         .createSignInIntentBuilder()
//         .setAvailableProviders(providers)
//         .build()
//
//
//
////        var button2 = findViewById<Button>(R.id.save)
////
////        button2.setOnClickListener {
////            save()
////        }
//
//
//
//
//     override fun onCreateView(
//         inflater: LayoutInflater,
//         container: ViewGroup?,
//         savedInstanceState: Bundle?
//     ): View? {
//         val binding=FragmentLoginBinding.inflate(inflater)
//         val button:Button= binding.button4
//         val user = FirebaseAuth.getInstance().currentUser
//
//         if (user == null) {
//             button.setOnClickListener {
//                 signInLauncher.launch(signInIntent)
//                 button.text = "SignIn" }
//         } else {
//             button.text = "SignOut"
//             val action = LoginFragmentDirections.actionLoginFragmentToBlankFragment()
//             findNavController().navigate(action)
////             button?.setOnClickListener { AuthUI.getInstance().signOut(getContext()) }
//         }
//         return binding.root
//     }
//
//     @SuppressLint("SetTextI18n")
//     private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
//         if (result.resultCode == RESULT_OK) {
//             val user = FirebaseAuth.getInstance().currentUser
//
//
////             if (user == null) {
////                     button?.setOnClickListener {
////                         signInLauncher.launch(signInIntent)
////                         button.text = "SignIn" }
////                 } else {
////                     button?.text = "SignOut"
////                     val action = LoginFragmentDirections.actionLoginFragmentToListFragment()
////                     findNavController().navigate(action)
//////             button?.setOnClickListener { AuthUI.getInstance().signOut(getContext()) }
////                 }
//
//
//         } else {
//        println("error")
//         }
//     }
//
//
////
////     fun save() {
////         val db = Firebase.firestorm
////         val user = hashMapOf(
////             "first" to "Email",
////             "last" to "Password"
////         )
////
////// Add a new document with a generated ID
////         db.collection("login")
////             .add(user)
////             .addOnSuccessListener { documentReference ->
////                 Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
////             }
////             .addOnFailureListener { e ->
////                 Log.w("TAG", "Error adding document", e)
////             }
////     }
//     }