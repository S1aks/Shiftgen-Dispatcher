package com.s1aks.shiftgen_dispatcher.data.api

import com.s1aks.shiftgen_dispatcher.data.api.modules.auth.AuthCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.directions.DirectionsCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.shifts.ShiftsCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures.StructuresCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.timesheets.TimeSheetsCase
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.workers.WorkersCase

interface ApiService : AuthCase, DirectionsCase, ShiftsCase, StructuresCase, TimeSheetsCase,
    WorkersCase {

    companion object {
        internal const val BASE_URL = "http://shiftgen.ru:8080"
    }
}

