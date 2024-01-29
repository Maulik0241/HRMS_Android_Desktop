package com.example.hrmsbackend

class DesktopPlatform : Platform {
    override val name: String = "Desktop"
}

fun getPlatform(): Platform = DesktopPlatform()