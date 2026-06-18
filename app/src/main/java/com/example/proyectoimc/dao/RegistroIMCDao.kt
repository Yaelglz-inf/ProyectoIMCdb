package com.example.proyectoimc.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyectoimc.model.RegistroIMC
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistroIMCDao {

    @Insert
    suspend fun insertar(registro: RegistroIMC)

    @Query("SELECT * FROM registros_imc ORDER BY id DESC")
    fun obtenerTodos(): Flow<List<RegistroIMC>>
}
