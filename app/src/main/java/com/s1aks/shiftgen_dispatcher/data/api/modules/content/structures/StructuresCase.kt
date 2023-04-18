package com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures

import com.s1aks.shiftgen_dispatcher.data.api.ApiService.Companion.BASE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.IdRequest
import io.ktor.http.HttpStatusCode

interface StructuresCase {
    suspend fun structures(): StructuresResponse
    suspend fun structureGet(idRequest: IdRequest): StructureResponse
    suspend fun structureInsert(structureRequest: StructureRequest): HttpStatusCode
    suspend fun structureUpdate(structureRequest: StructureRequest): HttpStatusCode
    suspend fun structureDelete(idRequest: IdRequest): HttpStatusCode

    companion object {
        const val STRUCTURES = "$BASE_URL/structures"
        const val STRUCTURE_GET = "$BASE_URL/structure/get"
        const val STRUCTURE_INSERT = "$BASE_URL/structure/insert"
        const val STRUCTURE_UPDATE = "$BASE_URL/structure/update"
        const val STRUCTURE_DELETE = "$BASE_URL/structure/delete"
    }
}