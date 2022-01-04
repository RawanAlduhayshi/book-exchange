package com.rawanalduhyshi.bookexchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDelegate

import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.rawanalduhyshi.bookexchange.databinding.FragmentProfileBinding



class ProfileFragment : Fragment() {

    private var mDelegate: AppCompatDelegate? = null
    private var mDrawer: DrawerLayout? = null
    private var toolbar: Toolbar? = null
    private val nvDrawer: NavigationView? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
   val binding = FragmentProfileBinding.inflate(inflater)
       toolbar = binding.toolbar
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }



}