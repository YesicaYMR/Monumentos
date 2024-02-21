package com.ymr.rmachor.vista

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ymr.rmachor.adaptadores.AdaptadorMonumento
import com.ymr.rmachor.controlador.EventosListener

import com.ymr.rmachor.databinding.ActivityVistaMonumentosBinding
import com.ymr.rmachor.controlador.Empresa
import com.ymr.rmachor.datos.Monumento

class VistaMonumentos : AppCompatActivity(), EventosListener {
    private lateinit var binding: ActivityVistaMonumentosBinding
    private lateinit var gridLayout: GridLayoutManager
    private lateinit var adaptador: AdaptadorMonumento
    private var empresa: Empresa? = null

    // Se ejecuta cuando se lanza la aplicación, se llama solo una vez
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Infla la vista utilizando el objeto de enlace generado por ViewBinding
        binding = ActivityVistaMonumentosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtiene el objeto Empresa de los extras del intent que inició esta actividad
        empresa = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("empresa", Empresa::class.java)
        } else {
            intent.getParcelableExtra<Empresa>("empresa")
        }
        // Configura el Listener del botón FAB para llamar a la función altaMonumento()
        binding.fab.setOnClickListener() {
            altaMonumento()
        }
    }

    // Se llama cuando la actividad vuelve al primer plano desde el segundo plano
    override fun onResume() {
        super.onResume()
        configurarRecycler()
    }

    // Función para iniciar la actividad de alta de monumento
    private fun altaMonumento() {
        val intent2 = Intent(this, AltaMonumento::class.java)
        intent2.putExtra("empresa", empresa)
        // Lanza la actividad y se registra para obtener un resultado
        resultadoAlta.launch(intent2)
    }

    // Configura el RecyclerView
    private fun configurarRecycler() {
        // Define el diseño y el número de columnas del RecyclerView
        gridLayout = GridLayoutManager(this, 1)
        adaptador = AdaptadorMonumento(empresa!!.monumentos, this)
        // Aplica las características al RecyclerView utilizando el objeto de enlace
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = gridLayout
            adapter = adaptador
        }
    }

    // Función para editar un monumento
    override fun editar(monumento: Monumento) {
        intent = Intent(this, DetalleMonumento::class.java)
        intent.putExtra("monumento", monumento)
        startActivity(intent)
    }

    // Listener para el resultado de la actividad de alta de monumento
    var resultadoAlta =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { resultado ->
            // Verifica si se ha vuelto de la actividad de alta de monumento con éxito
            if (resultado.resultCode == Activity.RESULT_OK) {
                val data: Intent? = resultado.data
                if (data != null) {
                    val monumento = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        data.getParcelableExtra("monumento", Monumento::class.java)
                    } else {
                        data.getParcelableExtra<Monumento>("monumento")
                    }
                    // Agrega el monumento a la empresa y notifica al adaptador del cambio
                    if(monumento!=null) {
                        val pos = empresa!!.anadir(monumento)
                        adaptador.notifyItemInserted(pos)
                    }
                }
            }
            // Si se canceló la actividad de alta de monumento, muestra un Snackbar
            else {
                Snackbar.make(binding.root, " Operación cancelada",
                    Snackbar.LENGTH_LONG).show()
            }
        }

}