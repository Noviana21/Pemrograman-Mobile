package com.daduuu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainActivityViewModel: ViewModel() {

    private val _dice1 = MutableLiveData<Int>()
    val dice1: LiveData<Int> get() = _dice1

    private val _dice2 = MutableLiveData<Int>()
    val dice2: LiveData<Int> get() = _dice2

    fun rollDice() {
        val dice = listOf(
            R.drawable.dice_1,
            R.drawable.dice_2,
            R.drawable.dice_3,
            R.drawable.dice_4,
            R.drawable.dice_5,
            R.drawable.dice_6
        )

        _dice1.value = dice.random()
        _dice2.value = dice.random()
    }
}