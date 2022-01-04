package com.rawanalduhyshi.bookexchange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import java.lang.Exception

class DrawerActivity : AppCompatActivity() {
    private var mDrawer: DrawerLayout? = null
    private var toolbar: Toolbar? = null
    private val nvDrawer: NavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
//        toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//
//        // This will display an Up icon (<-), we will replace it with hamburger later
//        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer =findViewById(R.id.drawer_layout) as  DrawerLayout
    }
    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        when (item.getItemId()) {
            android.R.id.home -> {
                mDrawer!!.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun selectDrawerItem(menuItem: MenuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        var fragment: Fragment? = null
        val addedBooks = ListBookAddedFragment()

        when (menuItem.itemId) {
        R.id.book_added_fragment-> addedBooks

            else ->{}
        }
//        try {
//            fragment = fragmentClass.newInstance() as Fragment
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

        // Insert the fragment by replacing any existing fragment
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.flContent, addedBooks).commit()
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.isChecked = true
        // Set action bar title
        title = menuItem.title
        // Close the navigation drawer
        mDrawer!!.closeDrawers()
    }
}