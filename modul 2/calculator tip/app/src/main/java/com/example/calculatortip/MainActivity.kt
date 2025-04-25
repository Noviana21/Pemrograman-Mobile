package com.example.calculatortip

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.calculatortip.databinding.ActivityMainBinding
import android.view.View
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(2000)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.tipResult.observe(this) { tip ->
            binding.tipAmount.text = "Tip Amount: $tip"
            binding.tipAmount.visibility = View.VISIBLE
        }

        binding.calculateButton.setOnClickListener {
            val inputEditText = binding.inputText.text.toString()
            val radioGroup = binding.radioGroup.checkedRadioButtonId
            val switchRound = binding.switchRound.isChecked

            val inputValue = inputEditText.toDoubleOrNull()
            if (inputValue == null) {
                Toast.makeText(this, "Mohon isi Cost Of Value dengan angka", Toast.LENGTH_SHORT).show()
                binding.tipAmount.text = ""
                binding.tipAmount.visibility = View.GONE
                return@setOnClickListener
            } else if (inputValue == 0.0) {
                Toast.makeText(this, "Mohon isi dengan angka yang lebih besar", Toast.LENGTH_SHORT).show()
                binding.tipAmount.text = ""
                binding.tipAmount.visibility = View.GONE
                return@setOnClickListener
            }

            viewModel.calculate(inputEditText, radioGroup, switchRound)
        }

    }
}