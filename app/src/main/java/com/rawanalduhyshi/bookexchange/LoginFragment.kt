package com.rawanalduhyshi.bookexchange
import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.rawanalduhyshi.bookexchange.databinding.FragmentLoginBinding


 abstract class LoginFragment : Fragment() {

     private val providers = arrayListOf(
         AuthUI.IdpConfig.EmailBuilder().build(),
         AuthUI.IdpConfig.PhoneBuilder().build(),
         AuthUI.IdpConfig.GoogleBuilder().build(),
         AuthUI.IdpConfig.FacebookBuilder().build(),
         AuthUI.IdpConfig.TwitterBuilder().build())

     // Create and launch sign-in intent
      private val signInIntent = AuthUI.getInstance()
         .createSignInIntentBuilder()
         .setAvailableProviders(providers)
         .build()


    private lateinit var binding:FragmentLoginBinding
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }
    // [END auth_fui_create_launcher]

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setHasOptionsMenu(false)
     }

     override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         binding = FragmentLoginBinding.inflate(inflater, container, false)
         return binding.root
     }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
//         binding.button.setOnClickListener {
//             signInLauncher.launch(signInIntent)
//         }

     }

    // [START auth_fui_result]
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser

        }
    }}
    // [END auth_fui_result]

