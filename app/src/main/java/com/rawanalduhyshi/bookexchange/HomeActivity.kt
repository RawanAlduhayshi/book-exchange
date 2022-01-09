package com.rawanalduhyshi.bookexchange

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)


        val homeFragment = ListFragment()
        val profileFragment = UserProfileFragment()
        val addBookFragment = AddBookFragment()
        var requestBookFragment = RequestBookFragment()
//        setCurrentFragment(homeFragment)
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.navController
//        setupActionBarWithNavController(navController)
        val buttomNav = findViewById<BottomNavigationView>(R.id.buttom_navigation)
        val navController= findNavController(R.id.nav_host_fragment)
        buttomNav.setupWithNavController(navController)
//val btnNavView=findViewById<BottomNavigationView>(R.id.bottomNavigationView)
// val navController= findNavController(R.id.HomeNavigationFragment)
//  btnNavView.setUpWithNavController(navController)
//        if (FirebaseAuth.getInstance().currentUser != null) {
//            makeCurrentFragment(blankFragment)
//        } else {
//            makeCurrentFragment(blankFragment)
//        }

        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.listFragment -> setCurrentFragment(homeFragment)
                R.id.userProfileFragment -> {
                    Toast.makeText(this, "~User profile", Toast.LENGTH_SHORT).show()
                    setCurrentFragment(profileFragment)
                }
                R.id.addBookFragment ->setCurrentFragment(addBookFragment)
                R.id.requestBookFragment -> setCurrentFragment(requestBookFragment)
//                else -> {}
            }
            true
        }

    }


    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

//search api
//https://www.googleapis.com/books/v1/volumes?q=search-terms&key=your-API-key