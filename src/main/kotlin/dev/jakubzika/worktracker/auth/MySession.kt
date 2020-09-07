package dev.jakubzika.worktracker.auth

const val SESSION_NAME = "MY_SESSION"

data class MySession(val id: String, val userId: Int)