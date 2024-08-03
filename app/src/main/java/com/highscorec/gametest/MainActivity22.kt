package com.highscorec.gametest

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.highscorec.gametest.databinding.ActivityMain22Binding

private  lateinit var binding: ActivityMain22Binding

class MainActivity22 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain22Binding.inflate(layoutInflater)
        setContentView(binding.root)

        init22()
    }

    fun init22() {

        binding.button2.setOnClickListener() {

            onBackPressed()
            finish()


        }
    }
}