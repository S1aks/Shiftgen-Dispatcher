package com.s1aks.shiftgen_dispatcher.domain

import com.s1aks.shiftgen_dispatcher.data.entities.Direction
import com.s1aks.shiftgen_dispatcher.data.entities.LoginData
import com.s1aks.shiftgen_dispatcher.data.entities.RegisterData
import com.s1aks.shiftgen_dispatcher.data.entities.Shift
import com.s1aks.shiftgen_dispatcher.data.entities.Structure
import com.s1aks.shiftgen_dispatcher.data.entities.StructuresMap
import com.s1aks.shiftgen_dispatcher.data.entities.TimeBlock
import com.s1aks.shiftgen_dispatcher.data.entities.TimeSheet
import com.s1aks.shiftgen_dispatcher.data.entities.TokensData
import com.s1aks.shiftgen_dispatcher.data.entities.Worker
import io.ktor.http.HttpStatusCode
import java.time.YearMonth

interface Repository {
    suspend fun access(): HttpStatusCode
    suspend fun login(loginData: LoginData): TokensData
    suspend fun register(registerData: RegisterData, structureId: Int): TokensData

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

    suspend fun getStructures(): StructuresMap
    suspend fun getStructureId(): Int
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