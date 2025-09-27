package com.dmmeta.yatrago

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform