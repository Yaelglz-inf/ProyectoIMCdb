package com.example.proyectoimc.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "registros_imc")
data class RegistroIMC(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val edad: Int,
    val peso: Double,
    val estatura: Double,
    val imc: Double,
    val categoria: String
)
