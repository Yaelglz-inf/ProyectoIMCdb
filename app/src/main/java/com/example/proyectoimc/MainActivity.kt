package com.example.proyectoimc

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoimc.databinding.ActivityMainBinding
import com.example.proyectoimc.model.RegistroIMC
import com.example.proyectoimc.viewmodel.RegistroIMCViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RegistroIMCViewModel

    private var ultimoImc: Double = 0.0
    private var ultimaCategoria: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(RegistroIMCViewModel::class.java)

        binding.bttCalcular.setOnClickListener {
            calcularIMC()
        }


        binding.bttResultados.setOnClickListener {
            startActivity(Intent(this, Resultados::class.java))
        }

        binding.bttGuardar.setOnClickListener {
            guardarRegistro()
        }

    }

    private fun calcularIMC() {
        val nombre = binding.etNombre.text.toString()
        val pesoStr = binding.etPeso.text.toString()
        val estaturaStr = binding.etEstatura.text.toString()

        if (nombre.isEmpty() || pesoStr.isEmpty() || estaturaStr.isEmpty()) {
            Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val peso = pesoStr.toDouble()
        val estatura = estaturaStr.toDouble() / 100
        val imc = peso / (estatura * estatura)

        val categoria = when {
            imc < 18.5 -> "Bajo peso"
            imc < 25.0 -> "Peso normal"
            imc < 30.0 -> "Sobrepeso"
            else       -> "Obesidad"
        }

        val categoriaDescrip = when {
            imc < 18.5 -> "${nombre} tu IMC indica que tu peso es inferior al rango considerado saludable, estar por debajo del peso recomendado puede dejar a tu cuerpo sin las reservas de energía necesarias y debilitar tu sistema inmunológico."
            imc < 25.0 -> "¡Felicidades ${nombre}! Tu peso se encuentra dentro del rango ideal para tu estatura, esto indica un buen equilibrio entre la energía que consumes y la que gastas, el objetivo ahora es mantener este balance y enfocarte en tu salud a largo plazo."
            imc < 30.0 -> "${nombre} tu IMC se encuentra en la categoría de sobrepeso, esto funciona como una señal de advertencia de tu cuerpo. No se trata de hacer dietas extremas, sino de realizar pequeños ajustes en tu rutina diaria que generen un gran impacto en tu energía y salud a futuro."
            else       -> "${nombre} tu IMC indica obesidad, en este punto, el exceso de peso puede comenzar a elevar el riesgo de desarrollar condiciones como presión arterial alta, colesterol elevado o resistencia a la insulina."
        }

        ultimoImc = imc
        ultimaCategoria = categoria

        binding.tvwIMcResultado.text = "IMC: ${"%.2f".format(imc)} — $categoria"

        binding.tvwResultadoDescrp.text = "$categoriaDescrip"
    }

    private fun guardarRegistro() {
        val nombre = binding.etNombre.text.toString()
        val pesoStr = binding.etPeso.text.toString()
        val estaturaStr = binding.etEstatura.text.toString()
        val edadStr = binding.etEdad.text.toString()

        if (nombre.isEmpty() || pesoStr.isEmpty() || estaturaStr.isEmpty() || edadStr.isEmpty()) {
            Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (ultimoImc == 0.0) {
            Toast.makeText(this, "Primero calcula el IMC", Toast.LENGTH_SHORT).show()
            return
        }

        val registro = RegistroIMC(
            nombre = nombre,
            edad = edadStr.toInt(),
            peso = pesoStr.toDouble(),
            estatura = estaturaStr.toDouble(),
            imc = ultimoImc,
            categoria = ultimaCategoria
        )

        viewModel.guardar(registro)
        Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show()
    }

}
