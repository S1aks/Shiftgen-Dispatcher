package com.s1aks.shiftgen_dispatcher.di

import com.s1aks.shiftgen_dispatcher.data.LocalSecureStore
import com.s1aks.shiftgen_dispatcher.data.RepositoryImpl
import com.s1aks.shiftgen_dispatcher.data.api.ApiService
import com.s1aks.shiftgen_dispatcher.data.api.ApiServiceImpl
import com.s1aks.shiftgen_dispatcher.data.api.KtorClient
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
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.shifts.GetYearMonthsUseCase
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
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataAccessModule = module {
    singleOf(::LocalSecureStore)
    singleOf(::KtorClient)
    single { ApiServiceImpl(client = get<KtorClient>().instance) } bind ApiService::class
    singleOf(::RepositoryImpl) bind Repository::class
}

val useCasesModule = module {
    singleOf(::CheckAuthorizationUseCase)
    singleOf(::SendLoginDataUseCase)
    singleOf(::SendRegisterDataUseCase)

    singleOf(::GetDirectionsUseCase)
    singleOf(::GetDirectionUseCase)
    singleOf(::InsertDirectionUseCase)
    singleOf(::UpdateDirectionUseCase)
    singleOf(::DeleteDirectionUseCase)

    singleOf(::GetShiftsUseCase)
    singleOf(::GetShiftUseCase)
    singleOf(::InsertShiftUseCase)
    singleOf(::UpdateShiftUseCase)
    singleOf(::DeleteShiftUseCase)
    singleOf(::GetYearMonthsUseCase)

    singleOf(::GetStructuresUseCase)
    singleOf(::GetStructureUseCase)
    singleOf(::InsertStructureUseCase)
    singleOf(::UpdateStructureUseCase)
    singleOf(::DeleteStructureUseCase)

    singleOf(::GetWorkersUseCase)
    singleOf(::GetWorkerUseCase)
    singleOf(::InsertWorkerUseCase)
    singleOf(::UpdateWorkerUseCase)
    singleOf(::DeleteWorkerUseCase)
}

val viewModelsModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::StructureViewModel)
    viewModelOf(::ShiftsViewModel)
    viewModelOf(::ShiftEditViewModel)
    viewModelOf(::DirectionsViewModel)
    viewModelOf(::DirectionEditViewModel)
    viewModelOf(::WorkersViewModel)
    viewModelOf(::WorkerEditViewModel)
}
