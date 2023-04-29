package com.s1aks.shiftgen_dispatcher.data.api.modules.content.time_blocks

import com.s1aks.shiftgen_dispatcher.data.api.ApiService.Companion.BASE_URL
import com.s1aks.shiftgen_dispatcher.data.api.modules.content.IdRequest
import io.ktor.http.HttpStatusCode

interface TimeBlocksCase {
    suspend fun timeBlocks(): TimeBlocksResponse
    suspend fun timeBlockGet(idRequest: IdRequest): TimeBlockResponse
    suspend fun timeBlockInsert(timeBlockRequest: TimeBlockRequest): HttpStatusCode
    suspend fun timeBlockUpdate(timeBlockRequest: TimeBlockRequest): HttpStatusCode
    suspend fun timeBlockDelete(idRequest: IdRequest): HttpStatusCode

    companion object {
        const val TIME_BLOCKS_URL = "$BASE_URL/time_blocks"
        const val TIME_BLOCK_GET_URL = "$BASE_URL/time_block/get"
        const val TIME_BLOCK_INSERT_URL = "$BASE_URL/time_block/insert"
        const val TIME_BLOCK_UPDATE_URL = "$BASE_URL/time_block/update"
        const val TIME_BLOCK_DELETE_URL = "$BASE_URL/time_block/delete"
    }
}