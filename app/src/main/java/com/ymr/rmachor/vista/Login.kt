package com.ymr.rmachor.vista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ymr.rmachor.R
import com.ymr.rmachor.databinding.ActivityLoginBinding
import com.ymr.rmachor.controlador.Empresa
import com.ymr.rmachor.datos.Monumento
import com.ymr.rmachor.datos.Usuario

class Login : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLoginBinding
    private lateinit var empresa: Empresa
    private var intentos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Infla la vista de la actividad utilizando ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Carga la imagen utilizando la función loadImagen()
        loadImagen()
        // Inicializa el objeto empresa con una lista de usuarios y monumentos
        empresa = Empresa(ArrayList<Usuario>(),ArrayList<Monumento>())
        empresa.anadirUsuarios()
        empresa.anadirMonumentos()

        // Configura el Listener del botón "Cancelar" para finalizar la actividad
        binding.btnCancelar.setOnClickListener {
            finish()
        }

        // Configura el Listener del botón "Aceptar" para llamar a la función comprobarLogin()
        binding.btnAceptar.setOnClickListener {
            comprobarLogin()
        }
    }

    // Función para cargar una imagen utilizando la biblioteca Glide
    private fun loadImagen(url: String = "https://i.etsystatic.com/9520660/r/il/d0bc0b/988508318/il_570xN.988508318_i4mn.jpg") {
        Glide.with(this)
            .load(url)//dirección de internet
            .centerCrop()//que lo centre y lo ajuste
            .diskCacheStrategy(DiskCacheStrategy.ALL)//almacenar la imagen en caché o no
            .error(R.drawable.ic_error)//imagen de error en caso de no encontrar la imagen
            .into(binding.imagen)
    }

    // Función para comprobar el login
    private fun comprobarLogin() {
        var nombre = binding.txtLogin?.getText().toString()
        var contra = binding.txtContrasena?.text.toString()
        if (comprobarDatos(nombre, contra)) {
            var pos = empresa.buscar(nombre, contra)
            if (pos != -1) {
                // Abrir nueva ventana pasando el nombre del usuario registrado
                var intent = Intent(this, VistaMonumentos::class.java)
                intent.putExtra("empresa", empresa)
                startActivity(intent)

                // Borrar el texto de los textfields
                limpiar()
            } else {
                binding.error.text = getString(R.string.errorLogin)
                binding.txtContrasena.setText("")
                intentos++
                if (intentos > 3) {
                    finishAffinity()
                }
            }
        }
    }

    // Función para limpiar los campos de texto
    private fun limpiar() {
        binding.txtLogin.setText("")
        binding.txtContrasena.setText("")
    }

    // Función para comprobar que los datos de usuario y contraseña no estén vacíos
    private fun comprobarDatos(nombre: String, contra: String): Boolean {
        var resultado = false
        if (nombre.isBlank()) {
            binding.error.text = getString(R.string.nombreVacio)
        } else if (contra.isBlank()) {
            binding.error.text = getString(R.string.contraVacia)
        } else {
            resultado = true
        }
        return resultado
    }

}