package dev.jakubzika.worktracker.exception

import java.lang.RuntimeException

class NotFoundException(message: String): RuntimeException(message)