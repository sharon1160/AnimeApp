package com.example.animeapp.presentation.ui.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.domain.usecase.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState = _uiState.asStateFlow()

    fun searchCharacter(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(detailedCharacter = getCharacterUseCase.execute(id = id))
            }
        }
    }
}
