package com.dmmeta.choloja

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform