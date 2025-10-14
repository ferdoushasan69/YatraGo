package com.dmmeta.choloja.presentation

import com.dmmeta.choloja.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}