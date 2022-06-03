package com.parita.paginationapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.parita.paginationapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(CharacterFragment())
    }

    fun replaceFragment(someFragment: Fragment) {
        val transaction: FragmentTransaction =
            supportFragmentManager.beginTransaction() as FragmentTransaction
        transaction.replace(R.id.frame_container, someFragment).addToBackStack(null).commit()
    }

}