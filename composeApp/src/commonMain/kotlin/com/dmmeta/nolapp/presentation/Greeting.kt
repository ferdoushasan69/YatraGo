package com.dmmeta.nolapp.presentation

import com.dmmeta.nolapp.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}