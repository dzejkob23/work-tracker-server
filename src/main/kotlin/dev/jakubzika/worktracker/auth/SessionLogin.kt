package dev.jakubzika.worktracker.auth

const val SESSION_LOGIN_NAME = "work-tracker-session-login"

data class SessionLogin(val userId: Long, val userName: String)