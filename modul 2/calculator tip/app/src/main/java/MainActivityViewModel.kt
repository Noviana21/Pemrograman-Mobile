package com.example.calculatortip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivityViewModel: ViewModel() {
    private val _tipResult = MutableLiveData<String>()
    val tipResult: LiveData<String> = _tipResult

    fun calculate(inputEditText: String?, radioGroup: Int, switchRound: Boolean) {
        val cost = inputEditText?.toDoubleOrNull()
        if (cost == null) {
            _tipResult.value = ""
            return
        }

        val tipPercentage = when (radioGroup) {
            R.id.amazing_button -> 0.20
            R.id.good_button -> 0.18
            else -> 0.15
        }

        var tip = tipPercentage * cost

        if (switchRound) {
            tip = ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

        _tipResult.value = formattedTip
    }


}