package com.s1aks.shiftgen_dispatcher.utils

import com.s1aks.shiftgen_dispatcher.data.entities.Periodicity
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// Экстеншн для перевода рабочего времени millis в строку вывода HH:mm
fun Long.toTimeString(): String {
    val hours = this / 1000 / 60 / 60
    val minutes = this / 1000 / 60 % 60
    return String.format("%02d:%02d", hours, minutes)
}

// Экстеншн для перевода строки HH:mm в millis
fun String.timeToMillis(): Long =
    split(':')[0].toLong() * 60 * 60 * 1000 + split(':')[1].toLong() * 60 * 1000

fun Long.getHours(): Int {
    val hours = this / 1000 / 60 / 60
    return hours.toInt()
}

fun Long.getMinutes(): Int {
    val minutes = this / 1000 / 60 % 60
    return minutes.toInt()
}


fun Worker.fio(): String =
    this.let {
        StringBuilder()
            .append(lastName)
            .append(if (firstName.isNotBlank()) " ${firstName.first()}." else "")
            .append(if (!patronymic.isNullOrBlank()) " ${patronymic.first()}." else "")
            .toString()
    }

fun LocalDateTime.toDayString() = format(DateTimeFormatter.ofPattern("dd.MM.yy")) ?: ""
fun LocalDateTime.toTimeString() = format(DateTimeFormatter.ofPattern("HH:mm")) ?: ""
fun LocalTime.toTimeString() = format(DateTimeFormatter.ofPattern("HH:mm")) ?: ""
fun LocalDateTime.toDateTimeString() = format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")) ?: ""
fun String.toLocalDateTime() =
    LocalDateTime.parse(this, DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"))
        ?: throw RuntimeException("Error parse string to LocalDateTime.")

fun String.getPeriodicity(): Periodicity =
    Periodicity.values().firstOrNull { it.label == this }
        ?: throw RuntimeException("Error parse Periodicity from string field.")