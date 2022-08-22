package com.mwaibanda.momentum.android.presentation.offer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OfferViewModel : ViewModel() {
    val offerKeypad = arrayOf(
        arrayOf('1', '2', '3'),
        arrayOf('4', '5', '6'),
        arrayOf('7', '8', '9'),
        arrayOf('•', '0', '<'),
    )
    val controlKeys = arrayOf('•', '<')
    private var isDecimalMode by mutableStateOf(false)
    var isKeypadEnabled by mutableStateOf(true)
        private set
    private val _number = MutableStateFlow("0")
    val number = _number.asStateFlow()
    private val _displayNumber = MutableStateFlow("0")
    val displayNumber = _displayNumber.asStateFlow()


    fun processInput(button: Char) {
        when (button) {
            '•' -> {
                processDecimal(::setDisplayText)
            }
            '<' -> {
                processBackspace(::setDisplayText)
            }
            else -> {
                processNumber(button, ::setDisplayText)
            }
        }
    }

    private fun setDisplayText(number: String) {
        if (isDecimalMode) {
            _displayNumber.value = getFormattedDecimalNumber(number)
        } else {
            _displayNumber.value = getFormattedNumber(number)
        }
    }

    private fun getFormattedNumber(number: String): String {
        when (number.count()) {
            4 -> {
                isKeypadEnabled = true
                return number.dropLast(3) + "," + number.drop(1)
            }
            5 -> {
                isKeypadEnabled = false
                return number.dropLast(3) + "," + number.drop(2)
            }
        }
        isKeypadEnabled = true
        return number
    }

    fun getFormattedDecimalNumber(number: String): String {
        when (number.dropLast(3).count()) {
            4 -> {
                return number.dropLast(6) + "," + number.drop(1)
            }
            5 -> {
                return number.dropLast(6) + "," + number.drop(2)
            }
        }
        return number
    }

    private fun processNumber(button: Char, onCompletion: (String) -> Unit) {
        if (_number.value == "0") {
            _number.value = button.toString()
        } else {
            if (isDecimalMode) {
                processDecimalModeNumber(button)
            } else {
                _number.value += button.toString()
            }
        }
        onCompletion(_number.value)

    }

    private fun processDecimal(onCompletion: (String) -> Unit) {
        if ((_number.value.count()) < 2) {
            isDecimalMode = true
            isKeypadEnabled = true
            _number.value += ".00"
        } else if (isDecimalMode && (_number.value[_number.value.count().minus(2)]) != '.') {
            isDecimalMode = false
            _number.value = _number.value.dropLast(3)
        } else {
            isDecimalMode = true
            isKeypadEnabled = true
            _number.value += ".00"
        }
        onCompletion(_number.value)
    }

    private fun processBackspace(onCompletion: (String) -> Unit) {
        if (_number.value.count() == 1) {
            _number.value = "0"
        } else {
            if (isDecimalMode) {
                processDecimalModeBackspace()
            } else {
                _number.value = _number.value.dropLast(1)
            }
        }
        onCompletion(_number.value)
    }

    private fun processDecimalModeNumber(button: Char) {
        if ((_number.value.dropLast(1).last()) == '0') {
            _number.value = _number.value.dropLast(2) + button + "0"
        } else {
            if ((_number.value.last()) == '0') {
                _number.value = _number.value.dropLast(1) + button
            }
        }
    }

    private fun processDecimalModeBackspace() {
        if (_number.value.dropLast(1).last().toString() + _number.value.last() == "00") {
            isDecimalMode = false
            _number.value = _number.value.dropLast(3)
        } else {
            if (_number.value.last() == '0') {
                _number.value = _number.value.dropLast(2) + "00"
            } else {
                _number.value = _number.value.dropLast(1) + "0"
            }
        }
    }
}