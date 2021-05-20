package com.zs.justplay.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zs.justplay.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainViewPager


    }
}