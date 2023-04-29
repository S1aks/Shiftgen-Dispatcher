package com.s1aks.shiftgen_dispatcher.domain.models

import java.time.YearMonth

data class TimeSheet(
    val id: Int,
    val workerId: Int,
    val structureId: Int,
    val periodYearMonth: YearMonth,
    var workedTime: Long,
    var calculatedTime: Long,
    val correctionTime: Long
)
