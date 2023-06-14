package com.s1aks.shiftgen_dispatcher.utils

import com.s1aks.shiftgen_dispatcher.data.entities.Action
import com.s1aks.shiftgen_dispatcher.data.entities.TimeBlock
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Экстеншн для перевода рабочего времени millis в строку вывода HH:mm
fun Long.toTimeString(): String {
    val hours = this / 1000 / 60 / 60
    val minutes = this / 1000 / 60 % 60
    return String.format("%02d:%02d", hours, minutes)
}

fun Worker?.fio(): String =
    this?.let {
        StringBuilder()
            .append(lastName)
            .append(if (firstName.isNotBlank()) " ${firstName.first()}." else "")
            .append(if (!patronymic.isNullOrBlank()) " ${patronymic.first()}." else "")
            .toString()
    } ?: ""

fun LocalDateTime.toDay() = format(DateTimeFormatter.ofPattern("dd.MM.y")) ?: ""
fun LocalDateTime.toTime() = format(DateTimeFormatter.ofPattern("HH:mm")) ?: ""
fun String.toLocalDateTime() =
    LocalDateTime.parse(this, DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"))
        ?: throw RuntimeException("Error parse string to LocalDateTime.")

fun List<TimeBlock>.toWorkTime() = filter { it.action == Action.WORK }.sumOf { it.duration }
    .toTimeString()