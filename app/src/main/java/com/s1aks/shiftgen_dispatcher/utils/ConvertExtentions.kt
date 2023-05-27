package com.s1aks.shiftgen_dispatcher.utils

import com.s1aks.shiftgen_dispatcher.data.entities.Worker

// Экстеншн для перевода рабочего времени millis в строку вывода HH:mm
fun Long.toTimeString(): String {
    val hours = this / 1000 / 60 / 60
    val minutes = this / 1000 / 60 % 60
    return String.format("%02d:%02d", hours, minutes)
}

// Экстеншн для выделения фамилии с инициалами из объекта рабочего
fun Worker?.fio(): String =
    this?.let {
        StringBuilder()
            .append(lastName)
            .append(if (firstName.isNotBlank()) " ${firstName.first()}." else "")
            .append(if (!patronymic.isNullOrBlank()) " ${patronymic.first()}." else "")
            .toString()
    } ?: ""



