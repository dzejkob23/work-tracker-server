package dev.jakubzika.worktracker.exceptions

import java.lang.RuntimeException

class NotFoundException(message: String): RuntimeException(message)