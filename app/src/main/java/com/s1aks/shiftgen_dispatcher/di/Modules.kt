package com.s1aks.shiftgen_dispatcher.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.RepositoryImpl
import com.s1aks.shiftgen_dispatcher.data.api.ApiService
import com.s1aks.shiftgen_dispatcher.domain.Repository
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.CheckAuthorizationUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.SendLoginDataUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.auth.SendRegisterDataUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.DeleteDirectionUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.GetDirectionUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.GetDirectionsUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.InsertDirectionUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.directions.UpdateDirectionUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts.DeleteShiftUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts.GetShiftUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts.GetShiftsUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts.InsertShiftUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts.UpdateShiftUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.structures.DeleteStructureUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.structures.GetStructureUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.structures.GetStructuresUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.structures.InsertStructureUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.structures.UpdateStructureUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers.DeleteWorkerUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers.GetWorkerUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers.GetWorkersUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers.InsertWorkerUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.workers.UpdateWorkerUseCase
import com.s1aks.shiftgen_dispatcher.ui.screens.auth.login.LoginViewModel
import com.s1aks.shiftgen_dispatcher.ui.screens.auth.register.RegisterViewModel
import com.s1aks.shiftgen_dispatcher.ui.screens.content.direction_edit.DirectionEditViewModel
import com.s1aks.shiftgen_dispatcher.ui.screens.content.directions.DirectionsViewModel
import com.s1aks.shiftgen_dispatcher.ui.screens.content.shift_edit.ShiftEditViewModel
import com.s1aks.shiftgen_dispatcher.ui.screens.content.shifts.ShiftsViewModel
import com.s1aks.shiftgen_dispatcher.ui.screens.content.structure.StructureViewModel
import com.s1aks.shiftgen_dispatcher.ui.screens.content.worker_edit.WorkerEditViewModel
import com.s1aks.shiftgen_dispatcher.ui.screens.content.workers.WorkersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataAccessModule = module {
    single {
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
}

val useCasesModule = module {
    single { CheckAuthorizationUseCase(repository = get(), localSecureStore = get()) }
    single { SendLoginDataUseCase(repository = get(), localSecureStore = get()) }
    single { SendRegisterDataUseCase(repository = get(), localSecureStore = get()) }

    single { GetDirectionsUseCase(repository = get()) }
    single { GetDirectionUseCase(repository = get()) }
    single { InsertDirectionUseCase(repository = get()) }
    single { UpdateDirectionUseCase(repository = get()) }
    single { DeleteDirectionUseCase(repository = get()) }

    single { GetShiftsUseCase(repository = get()) }
    single { GetShiftUseCase(repository = get()) }
    single { InsertShiftUseCase(repository = get()) }
    single { UpdateShiftUseCase(repository = get()) }
    single { DeleteShiftUseCase(repository = get()) }

    single { GetStructuresUseCase(repository = get()) }
    single { GetStructureUseCase(repository = get(), localSecureStore = get()) }
    single { InsertStructureUseCase(repository = get()) }
    single { UpdateStructureUseCase(repository = get()) }
    single { DeleteStructureUseCase(repository = get()) }

    single { GetWorkersUseCase(repository = get()) }
    single { GetWorkerUseCase(repository = get()) }
    single { InsertWorkerUseCase(repository = get()) }
    single { UpdateWorkerUseCase(repository = get()) }
    single { DeleteWorkerUseCase(repository = get()) }
}

val viewModelsModule = module {
    viewModel { LoginViewModel(sendLoginDataUseCase = get(), checkAuthorizationUseCase = get()) }
    viewModel { RegisterViewModel(getStructuresUseCase = get(), sendRegisterDataUseCase = get()) }
    viewModel { StructureViewModel(getStructureUseCase = get(), updateStructureUseCase = get()) }
    viewModel { ShiftsViewModel(getShiftsUseCase = get()) }
    viewModel { ShiftEditViewModel() }
    viewModel { DirectionsViewModel(getDirectionsUseCase = get(), deleteDirectionUseCase = get()) }
    viewModel {
        DirectionEditViewModel(
            getDirectionUseCase = get(),
            insertDirectionUseCase = get(),
            updateDirectionUseCase = get()
        )
    }
    viewModel { WorkersViewModel(getWorkersUseCase = get(), deleteWorkerUseCase = get()) }
    viewModel {
        WorkerEditViewModel(
            getWorkerUseCase = get(),
            insertWorkerUseCase = get(),
            updateWorkerUseCase = get(),
            getDirectionsUseCase = get()
        )
    }
}
