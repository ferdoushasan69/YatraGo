package com.dmmeta.nolapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform