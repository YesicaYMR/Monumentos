package com.ymr.rmachor.vista

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.ymr.rmachor.databinding.ActivityAltaMonumentoBinding
import com.ymr.rmachor.controlador.Empresa
import com.ymr.rmachor.datos.Monumento

class AltaMonumento : AppCompatActivity() {
    private lateinit var binding: ActivityAltaMonumentoBinding
    private var empresa: Empresa? = null
    private lateinit var monumento: Monumento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Infla la vista de la actividad utilizando ViewBinding
        binding = ActivityAltaMonumentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtiene el objeto Empresa de los extras de la intención que inició esta actividad
        empresa = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("empresa", Empresa::class.java)
        } else {
            intent.getParcelableExtra<Empresa>("empresa")
        }

        // Verifica si el objeto empresa no es nulo
        if (empresa != null) {
            // Configura el Listener del botón "Grabar" para llamar a la función grabar()
            binding.btnGrabar.setOnClickListener {
                grabar()
            }
            // Configura el Listener del botón "Regresar" para llamar a la función terminar()
            binding.btnRegresar.setOnClickListener {
                terminar()
            }
            // Actualiza la imagen cuando se introduce una URL en el campo de texto
            binding.etPhotoUrl.addTextChangedListener {
                cargarImagen(it.toString().trim())
            }
        }
    }

    // Función para cargar una imagen utilizando la biblioteca Glide
    private fun cargarImagen(url: String?) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.imagen)
    }

    // Función para guardar los datos del monumento
    private fun grabar() {
        // Obtiene el ID del monumento del campo de texto y lo convierte a Long
        val id = binding.etCodigo.text.toString().trim().toLong()
        monumento = Monumento(id)
        // Verifica si ya existe un monumento con ese ID
        if (empresa!!.existeMonumento(id) == -1) {
            // Guarda los datos del monumento
            guardar()
            // Devuelve el monumento creado como resultado
            val intentResultado = Intent()
            intentResultado.putExtra("monumento", monumento)
            setResult(RESULT_OK, intentResultado)
            finish()
        } else {
            // Muestra un mensaje de error si el ID del monumento ya existe
            binding.etCodigo.error = "Duplicado"
            Snackbar.make(
                binding.root, "Codigo Duplicado",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    // Función para guardar los datos del monumento en el objeto Monumento
    private fun guardar() {
        with(monumento!!) {
            id = binding.etCodigo.text.toString().trim().toLong()
            name = binding.etNombre.text.toString().trim()
            descripcion = binding.etDescripcion.text.toString().trim()
            photoUrl = binding.etPhotoUrl.text.toString().trim()
        }
    }

    // Función para finalizar la actividad con un resultado cancelado
    private fun terminar() {
        // Devuelve un resultado cancelado y finaliza la actividad
        setResult(RESULT_CANCELED)
        finish()
    }

}