package com.eshop.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

fun generateRandomString(length: Int = 25): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}

fun LocalDate.formatDate(formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")): String {
    return this.format(formatter)
}