package com.s1aks.shiftgen_dispatcher.di

import com.s1aks.shiftgen_dispatcher.data.api.ApiService
import org.koin.dsl.module

val appModule = module {
    single<ApiService> { ApiService.create() }

}