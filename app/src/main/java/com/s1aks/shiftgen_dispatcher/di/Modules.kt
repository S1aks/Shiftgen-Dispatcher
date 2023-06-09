package com.s1aks.shiftgen_dispatcher.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.RepositoryImpl
import com.s1aks.shiftgen_dispatcher.data.api.ApiService
import com.s1aks.shiftgen_dispatcher.domain.Repository
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.GetStructuresUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.SendLoginDataUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.SendRegisterDataUseCase
import com.s1aks.shiftgen_dispatcher.ui.screens.auth.login.LoginViewModel
import com.s1aks.shiftgen_dispatcher.ui.screens.auth.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataAccessModule = module {
    single<LocalSecureStore> {
        val context = get<Context>()
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "auth_tokens_secured",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        LocalSecureStore(sharedPreferences)
    }
    single<Repository> {
        val apiService = ApiService.create()
        RepositoryImpl(apiService)
    }
    viewModel { LoginViewModel(sendLoginDataUseCase = get()) }
    viewModel { RegisterViewModel(getStructuresUseCase = get(), sendRegisterDataUseCase = get()) }
}

val useCasesModule = module {
    single<SendLoginDataUseCase> {
        SendLoginDataUseCase(repository = get(), localSecureStore = get())
    }
    single<SendRegisterDataUseCase> {
        SendRegisterDataUseCase(repository = get(), localSecureStore = get())
    }
    single<GetStructuresUseCase> { GetStructuresUseCase(repository = get()) }
}