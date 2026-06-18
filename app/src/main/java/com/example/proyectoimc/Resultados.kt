package com.example.proyectoimc

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.proyectoimc.databinding.ActivityResultadosBinding
import com.example.proyectoimc.viewmodel.RegistroIMCViewModel
import kotlinx.coroutines.launch

class Resultados : AppCompatActivity() {

    lateinit var binding: ActivityResultadosBinding
    private lateinit var viewModel: RegistroIMCViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(RegistroIMCViewModel::class.java)

        lifecycleScope.launch {
            viewModel.registros.collect { lista ->
                if (lista.isEmpty()) {
                    binding.tvwList.text = "Todavía no hay registros guardados."
                } else {
                    binding.tvwList.text = lista.joinToString("\n\n") { registro ->
                        "${registro.nombre} (${registro.edad} años)\n" +
                                "Peso: ${registro.peso} kg — Estatura: ${registro.estatura} cm\n" +
                                "IMC: ${"%.2f".format(registro.imc)} — ${registro.categoria}"
                    }
                }
            }
        }
    }
}
