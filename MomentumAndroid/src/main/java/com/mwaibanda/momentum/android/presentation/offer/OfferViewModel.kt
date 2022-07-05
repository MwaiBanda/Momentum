package com.mwaibanda.momentum.android.presentation.offer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OfferViewModel: ViewModel() {
    val offerKeypad = arrayOf(
        arrayOf('1', '2', '3'),
        arrayOf('4', '5', '6'),
        arrayOf('7', '8', '9'),
        arrayOf('•', '0', '<'),
    )
    private var isDecimalMode by mutableStateOf(false)
    private val _number: MutableLiveData<String> = MutableLiveData("0")
    val number: LiveData<String> = _number


    fun processInput(button: Char) {
        when (button) {
            '•' -> { processDecimal() }
            '<' -> { processBackspace() }
            else -> { processNumber(button) }
        }
    }

    private fun processNumber(button: Char) {
        if (_number.value == "0") {
            _number.value = button.toString()
        }  else {
            if (isDecimalMode) {
                processDecimalModeNumber(button)
            } else {
                _number.value += button.toString()
            }
        }
    }
    private fun processDecimal(){
        if ((_number.value?.count() ?: 0) < 2 ) {
            isDecimalMode = true
            _number.value += ".00"
        } else if (isDecimalMode && (_number.value?.get(_number.value?.count()?.minus(2) ?: 0) ?: "") != '.') {
            isDecimalMode = false
            _number.value = _number.value?.dropLast(3)
        } else {
            isDecimalMode = true
            _number.value += ".00"
        }
    }
    private fun processBackspace() {
        if (_number.value?.count() == 1)
            _number.value = "0"
        else {
            if (isDecimalMode) {
                processDecimalModeBackspace()
            } else {
                _number.value = _number.value?.dropLast(1)
            }
        }
    }
    private fun processDecimalModeNumber(button: Char) {
        if ((_number.value?.dropLast(1)?.last() ?: "") == '0') {
            _number.value = _number.value?.dropLast(2) + button + "0"
        } else {
            if ((_number.value?.last() ?: "") == '0'){
                _number.value = _number.value?.dropLast(1) + button
            }
        }
    }
    private fun processDecimalModeBackspace(){
        if (_number.value?.dropLast(1)?.last().toString() + _number.value?.last() == "00") {
            isDecimalMode = false
            _number.value = _number.value?.dropLast(3)
        } else {
            if (_number.value?.last() == '0')
                _number.value = _number.value?.dropLast(2) + "00"
            else
                _number.value = _number.value?.dropLast(1) + "0"
        }
    }
}