package com.s1aks.shiftgen_dispatcher.ui.screens.content.structure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Structure
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.structures.GetStructureUseCase
import com.s1aks.shiftgen_dispatcher.utils.setFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StructureViewModel(
    private val getStructureUseCase: GetStructureUseCase
) : ViewModel() {
    private val _structureState: MutableStateFlow<ResponseState<Structure>> =
        MutableStateFlow(ResponseState.Idle)
    val structureState = _structureState.asStateFlow()

    init {
        viewModelScope.setFlow(_structureState) { getStructureUseCase.execute() }
    }
}