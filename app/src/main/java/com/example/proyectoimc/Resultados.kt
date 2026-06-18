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

    private lateinit var binding: ActivityResultadosBinding
    private lateinit var viewModel: RegistroIMCViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(RegistroIMCViewModel::class.java)

        lifecycleScope.launch {
            viewModel.registros.collect { lista ->
                if (lista.isEmpty()) {
                    binding.tvwList.text = "No hay registros guardados."
                } else {
                    val sb = StringBuilder()
                    lista.forEach { r ->
                        sb.append("👤 ${r.nombre} | Edad: ${r.edad}\n")
                        sb.append("   Peso: ${r.peso} kg | Estatura: ${r.estatura} cm\n")
                        sb.append("   IMC: ${"%.2f".format(r.imc)} — ${r.categoria}\n\n")
                    }
                    binding.tvwList.text = sb.toString()
                }
            }
        }
    }
}
