package com.s1aks.shiftgen_dispatcher.data.api.modules.content.structures

import com.s1aks.shiftgen_dispatcher.data.api.ApiService.Companion.BASE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.IdRequest
import io.ktor.http.HttpStatusCode

interface StructuresCase {
    suspend fun structures(): StructuresResponse
    suspend fun structureId(): StructureIdResponse
    suspend fun structureGet(idRequest: IdRequest): StructureResponse
    suspend fun structureInsert(structureRequest: StructureRequest): HttpStatusCode
    suspend fun structureUpdate(structureRequest: StructureRequest): HttpStatusCode
    suspend fun structureDelete(idRequest: IdRequest): HttpStatusCode

    companion object {
        const val STRUCTURES_URL = "$BASE_URL/structures"
        const val STRUCTURE_ID_URL = "$BASE_URL/structure_id"
        const val STRUCTURE_GET_URL = "$BASE_URL/structure/get"
        const val STRUCTURE_INSERT_URL = "$BASE_URL/structure/insert"
        const val STRUCTURE_UPDATE_URL = "$BASE_URL/structure/update"
        const val STRUCTURE_DELETE_URL = "$BASE_URL/structure/delete"
    }
}