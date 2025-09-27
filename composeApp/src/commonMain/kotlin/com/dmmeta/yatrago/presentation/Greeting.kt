package com.dmmeta.yatrago.presentation

import com.dmmeta.yatrago.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}