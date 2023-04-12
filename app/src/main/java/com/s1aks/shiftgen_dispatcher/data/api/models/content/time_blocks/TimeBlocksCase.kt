package com.s1aks.shiftgen_dispatcher.data.api.models.content.time_blocks

import com.s1aks.shiftgen_dispatcher.data.api.ApiService.Companion.BASE_URL
import com.s1aks.shiftgen_dispatcher.data.api.models.content.IdRequest

interface TimeBlocksCase {
    suspend fun timeBlocks(): TimeBlocksResponse
    suspend fun timeBlockGet(idRequest: IdRequest): TimeBlockResponse
    suspend fun timeBlockInsert(timeBlockRequest: TimeBlockRequest)
    suspend fun timeBlockUpdate(timeBlockRequest: TimeBlockRequest)
    suspend fun timeBlockDelete(idRequest: IdRequest)

    companion object {
        const val TIME_BLOCKS = "$BASE_URL/time_blocks"
        const val TIME_BLOCK_GET = "$BASE_URL/time_block/get"
        const val TIME_BLOCK_INSERT = "$BASE_URL/time_block/insert"
        const val TIME_BLOCK_UPDATE = "$BASE_URL/time_block/update"
        const val TIME_BLOCK_DELETE = "$BASE_URL/time_block/delete"
    }
}