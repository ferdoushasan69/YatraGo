package com.dmmeta.nolapp.presentation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform