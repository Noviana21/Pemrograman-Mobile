package com.daduuu

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.daduuu.databinding.ActivityMainBinding
import androidx.appcompat.widget.Toolbar
import com.daduuu.databinding.ToolBarBinding
import androidx.activity.viewModels
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivDice.setImageResource(R.drawable.dice_0)
        binding.ivDice2.setImageResource(R.drawable.dice_0)

        binding.btnRoll.setOnClickListener {
            viewModel.rollDice()
        }

        viewModel.dice1.observe(this) { hasil ->
            binding.ivDice.setImageResource(hasil)
        }

        viewModel.dice2.observe(this) { hasil ->
            binding.ivDice2.setImageResource(hasil)
            checkIfDouble()
        }

        val statusBarHeight = resources.getDimensionPixelSize(
            resources.getIdentifier("status_bar_height", "dimen", "android")
        )

        val toolbar: Toolbar = findViewById(R.id.toolBar)
        toolbar.setPadding(0, statusBarHeight, 0, 0)

    }

    private fun checkIfDouble() {
        val d1 = viewModel.dice1.value
        val d2 = viewModel.dice2.value

        if (d1 == d2) {
            Toast.makeText(this, "Selamat Anda dapat dadu double!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Anda belum beruntung!", Toast.LENGTH_SHORT).show()
        }
    }
}