package com.lee.beachcongetion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lee.beachcongetion.databinding.ActivityMainBinding
import com.lee.beachcongetion.fragment.AllBeachesFragment

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var binding : ActivityMainBinding
    private lateinit var mAllBeachFragment : AllBeachesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        savedInstanceState?:let {
            mAllBeachFragment = AllBeachesFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.container , mAllBeachFragment).commit()
        }
    }
}