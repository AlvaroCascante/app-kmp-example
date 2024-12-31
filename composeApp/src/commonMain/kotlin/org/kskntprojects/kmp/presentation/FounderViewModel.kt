package org.kskntprojects.kmp.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.kskntprojects.kmp.models.Founder
import org.kskntprojects.kmp.repositories.FounderRepository

sealed class FounderState {
    data object Loading : FounderState()
    data class Success(val founders: List<Founder>) : FounderState()
    data class Error(val exception: Throwable) : FounderState()
}

class FounderViewModel(private val repository: FounderRepository) : ViewModel() {

    private val _state = MutableStateFlow<FounderState>(FounderState.Loading)
    val state: StateFlow<FounderState> = _state

    init {
        fetchFounders()
    }

    private fun fetchFounders() {
        viewModelScope.launch {
            repository.getFounders().collect {
                _state.value = FounderState.Success(it)
            }
        }
    }
}