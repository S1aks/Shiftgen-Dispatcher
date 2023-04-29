package com.s1aks.shiftgen_dispatcher.domain

import com.s1aks.shiftgen_dispatcher.domain.models.Direction
import com.s1aks.shiftgen_dispatcher.domain.models.LoginData
import com.s1aks.shiftgen_dispatcher.domain.models.RegisterData
import com.s1aks.shiftgen_dispatcher.domain.models.Shift
import com.s1aks.shiftgen_dispatcher.domain.models.Structure
import com.s1aks.shiftgen_dispatcher.domain.models.TimeBlock
import com.s1aks.shiftgen_dispatcher.domain.models.TimeSheet
import com.s1aks.shiftgen_dispatcher.domain.models.TokensData
import com.s1aks.shiftgen_dispatcher.domain.models.Worker
import java.time.YearMonth

interface Repository {
    suspend fun login(loginData: LoginData): TokensData
    suspend fun register(registerData: RegisterData): TokensData

    suspend fun getDirections(): List<Direction>
    suspend fun getDirection(id: Int): Direction
    suspend fun insertDirection(direction: Direction): Boolean
    suspend fun updateDirection(direction: Direction): Boolean
    suspend fun deleteDirection(id: Int): Boolean

    suspend fun getShifts(yearMonth: YearMonth): List<Shift>
    suspend fun getShift(id: Int): Shift
    suspend fun insertShift(shift: Shift): Boolean
    suspend fun updateShift(shift: Shift): Boolean
    suspend fun deleteShift(id: Int): Boolean

    suspend fun getStructures(): Map<Int, String>
    suspend fun getStructure(id: Int): Structure
    suspend fun insertStructure(structure: Structure): Boolean
    suspend fun updateStructure(structure: Structure): Boolean
    suspend fun deleteStructure(id: Int): Boolean

    suspend fun getTimeBlocks(): List<TimeBlock>
    suspend fun getTimeBlock(id: Int): TimeBlock
    suspend fun insertTimeBlock(timeBlock: TimeBlock): Boolean
    suspend fun updateTimeBlock(timeBlock: TimeBlock): Boolean
    suspend fun deleteTimeBlock(id: Int): Boolean

    suspend fun getTimeSheet(): List<TimeSheet>
    suspend fun getTimeSheet(id: Int): TimeSheet
    suspend fun getTimeSheetByYearMonth(yearMonth: YearMonth): List<TimeSheet>
    suspend fun getTimeSheetByWorkerIdYearMonth(id: Int, yearMonth: YearMonth): List<TimeSheet>
    suspend fun insertTimeSheet(timeSheet: TimeSheet): Boolean
    suspend fun updateTimeSheet(timeSheet: TimeSheet): Boolean
    suspend fun deleteTimeSheet(id: Int): Boolean

    suspend fun getWorkers(): List<Worker>
    suspend fun getWorker(id: Int): Worker
    suspend fun insertWorker(worker: Worker): Boolean
    suspend fun updateWorker(worker: Worker): Boolean
    suspend fun deleteWorker(id: Int): Boolean
}