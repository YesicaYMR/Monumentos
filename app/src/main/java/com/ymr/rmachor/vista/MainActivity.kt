package com.ymr.rmachor.vista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ymr.rmachor.R
import com.ymr.rmachor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Crear variable para acceder a los elementos de la actividad
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar la vista de la actividad utilizando ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Cargar la imagen utilizando la función loadImagen()
        loadImagen()

        // Configurar el Listener del botón "Autor" para iniciar la actividad Autor
        binding.btnAutor.setOnClickListener() {
            intent = Intent(this, Autor::class.java) // Se crea una intención para iniciar la actividad Autor
            startActivity(intent) // Iniciar la actividad Autor
        }

        // Configurar el Listener del botón "Continuar" para iniciar la actividad Login
        binding.btnContinuar.setOnClickListener() {
            intent = Intent(this, Login::class.java) // Se crea una intención para iniciar la actividad Login
            startActivity(intent) // Iniciar la actividad Login
        }
    }

    // Función para cargar la imagen utilizando la biblioteca Glide
    private fun loadImagen(url: String = "https://i.etsystatic.com/9520660/r/il/d0bc0b/988508318/il_570xN.988508318_i4mn.jpg") {
        Glide.with(this)
            .load(url) // Especifica la URL de la imagen a cargar
            .centerCrop() // Ajusta la imagen al tamaño de la vista
            .diskCacheStrategy(DiskCacheStrategy.ALL) // Configura la estrategia de almacenamiento en caché
            .error(R.drawable.ic_error) // Establece la imagen de error en caso de que no se pueda cargar la imagen
            .into(binding.imagen) // Carga la imagen en el ImageView definido en el archivo de diseño
    }


}
