package com.example.proyectoimc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoimc.database.AppDatabase
import com.example.proyectoimc.model.RegistroIMC
import com.example.proyectoimc.repository.RegistroIMCRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RegistroIMCViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RegistroIMCRepository = RegistroIMCRepository(
        AppDatabase.getDatabase(application).registroIMCDao()
    )

    val registros: StateFlow<List<RegistroIMC>> = repository.obtenerTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun guardar(registro: RegistroIMC) {
        viewModelScope.launch {
            repository.insertar(registro)
        }
    }
}
