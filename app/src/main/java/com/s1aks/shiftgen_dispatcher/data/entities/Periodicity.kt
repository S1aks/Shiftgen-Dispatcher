package com.s1aks.shiftgen_dispatcher.data.entities

enum class Periodicity(val label: String) {
    ON_EVEN("По четным"),
    ON_ODD("По нечетным"),
    SINGLE("Единоразово"),
    DAILY("Ежедневно"),
    WEEKLY("Еженедельно")
}