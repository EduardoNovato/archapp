package com.eduardo.dev.archapp.viewmodel.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel : ViewModel() {
    private val _darkTheme = MutableStateFlow(false)
    val isDarkTheme: MutableStateFlow<Boolean> = _darkTheme

    fun toggleTheme(enable: Boolean) {
        viewModelScope.launch() {
            _darkTheme.emit(enable)
        }
    }
}