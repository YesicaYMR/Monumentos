package com.ymr.rmachor.vista

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ymr.rmachor.R
import com.ymr.rmachor.databinding.ActivityDetalleMonumentoBinding
import com.ymr.rmachor.databinding.ActivityVistaMonumentosBinding
import com.ymr.rmachor.datos.Monumento

class DetalleMonumento : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleMonumentoBinding
    private var monumento: Monumento?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Infla la vista de la actividad utilizando ViewBinding
        binding = ActivityDetalleMonumentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura el Listener del botón "Volver" para finalizar la actividad
        binding.btnVolver?.setOnClickListener {
            finish()
        }
        val fabSendEmail = findViewById<FloatingActionButton>(R.id.fabEnviarEmail)
        fabSendEmail.setOnClickListener {
            enviarCorreo()
        }

        // Recupera el objeto Monumento de los extras de la intención que inició esta actividad
        monumento = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("monumento", Monumento::class.java)
        } else {
            intent.getParcelableExtra<Monumento>("monumento")
        }

        // Visualiza los detalles del monumento
        visualizar()
    }

    // Función para visualizar los detalles del monumento en la vista
    private fun visualizar() {
        with(binding) {
            nombre.setText(monumento?.name)
            descripcion.setText(monumento?.descripcion)

            cargarImagen(monumento?.photoUrl)
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

    //Función para enviar el monumento por correo electrónico
    private fun enviarCorreo() {

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "message/rfc822"

// Destinatarios
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(""))

// Asunto y cuerpo del correo electrónico
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Te gustará este monumento de Burgos")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Nombre: ${monumento!!.name}\nDescripción: ${monumento!!.descripcion}\nImagen:${monumento!!.photoUrl}")


// Inicia el intento del correo electrónico
        startActivity(Intent.createChooser(emailIntent, "Enviar correo..."))


    }

}