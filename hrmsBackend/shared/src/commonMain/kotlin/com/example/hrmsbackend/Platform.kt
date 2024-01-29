package com.example.hrmsbackend

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform