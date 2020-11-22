package dev.jakubzika.worktracker.extension

import kotlinx.html.Tag

const val TRANSLATION_ATTR = "translation-key"

fun Tag.translationId(transId: String) {
    attributes[TRANSLATION_ATTR] = transId
}