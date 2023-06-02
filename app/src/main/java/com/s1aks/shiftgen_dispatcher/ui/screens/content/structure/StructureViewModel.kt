package com.s1aks.shiftgen_dispatcher.ui.screens.content.structure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.shiftgen_dispatcher.data.ResponseState
import com.s1aks.shiftgen_dispatcher.data.entities.Structure
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.structures.GetStructureUseCase
import com.s1aks.shiftgen_dispatcher.domain.usecases.content.structures.UpdateStructureUseCase
import com.s1aks.shiftgen_dispatcher.utils.setFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StructureViewModel(
    private val getStructureUseCase: GetStructureUseCase,
    private val updateStructureUseCase: UpdateStructureUseCase
) : ViewModel() {
    private val _structureState: MutableStateFlow<ResponseState<Structure>> =
        MutableStateFlow(ResponseState.Idle)
    val structureState = _structureState.asStateFlow()
    private val _updateStructureState: MutableStateFlow<ResponseState<Boolean>> =
        MutableStateFlow(ResponseState.Idle)
    val updateStructureState = _updateStructureState.asStateFlow()

    init {
        viewModelScope.setFlow(_structureState) { getStructureUseCase.execute() }
    }

    fun saveData(structure: Structure) {
        viewModelScope.setFlow(_updateStructureState) { updateStructureUseCase.execute(structure) }
    }

    fun clearStates() {
        _structureState.value = ResponseState.Idle
        _updateStructureState.value = ResponseState.Idle
    }
}