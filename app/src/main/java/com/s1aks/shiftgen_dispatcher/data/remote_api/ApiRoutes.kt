package com.s1aks.shiftgen_dispatcher.data.remote_api

object ApiRoutes {
    private const val BASE_URL = "http://shiftgen.ru"
    const val LOGIN = "$BASE_URL/login"
    const val REFRESH = "$BASE_URL/refresh"
    const val REGISTER = "$BASE_URL/register"

    const val DIRECTIONS = "$BASE_URL/directions"
    const val DIRECTION_GET = "$BASE_URL/direction/get"
    const val DIRECTION_INSERT = "$BASE_URL/direction/insert"
    const val DIRECTION_UPDATE = "$BASE_URL/direction/update"
    const val DIRECTION_DELETE = "$BASE_URL/direction/delete"
}