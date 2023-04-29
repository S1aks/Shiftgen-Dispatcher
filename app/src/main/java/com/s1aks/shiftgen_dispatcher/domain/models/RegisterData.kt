package com.s1aks.shiftgen_dispatcher.domain.models

data class RegisterData(
    val login: String,
    val email: String,
    val password: String,
    val group: String,
    val structure: String
)
