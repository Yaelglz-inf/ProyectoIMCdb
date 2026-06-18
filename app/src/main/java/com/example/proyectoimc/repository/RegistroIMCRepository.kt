package com.example.proyectoimc.repository

import com.example.proyectoimc.dao.RegistroIMCDao
import com.example.proyectoimc.model.RegistroIMC
import kotlinx.coroutines.flow.Flow

class RegistroIMCRepository(private val dao: RegistroIMCDao) {

    suspend fun insertar(registro: RegistroIMC) {
        dao.insertar(registro)
    }

    fun obtenerTodos(): Flow<List<RegistroIMC>> {
        return dao.obtenerTodos()
    }
}
